package com.example.farmmunity.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.farmmunity.R
import com.example.farmmunity.authentication.core.AuthenticationConstants.RC_GOOGLE_SIGN_IN
import com.example.farmmunity.authentication.core.AuthenticationUtils.Companion.printError
import com.example.farmmunity.authentication.core.PhoneAuthResult
import com.example.farmmunity.authentication.navigation.AuthenticationNavGraph
import com.example.farmmunity.home.presentation.HomeActivity
import com.example.farmmunity.ui.theme.FarmmunityTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var phoneAuthOptionsBuilder: PhoneAuthOptions.Builder

    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                authenticationViewModel.onPhoneAuthResultReceived(
                    PhoneAuthResult.OnCodeSent(p0)
                )
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                authenticationViewModel.onPhoneAuthResultReceived(
                    PhoneAuthResult.OnVerificationCompleted(p0)
                )
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                printError(p0.localizedMessage ?: p0.toString())
            }
        }

        phoneAuthOptionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setActivity(this)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callbacks)

        setContent {
            FarmmunityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navHostController = rememberNavController()
                    AuthenticationNavGraph(
                        navHostController = navHostController,
                        authenticationViewModel = authenticationViewModel,
                        phoneAuthOptionsBuilder = phoneAuthOptionsBuilder
                    )
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            authenticationViewModel.eventFlow.collect { event ->
                when (event) {
                    AuthenticationEvent.SignInWithGoogle -> {
                        this@AuthenticationActivity.startActivityForResult(
                            GoogleSignIn.getClient(
                                this@AuthenticationActivity,
                                googleSignInOptions
                            ).signInIntent,
                            RC_GOOGLE_SIGN_IN
                        )
                    }
                    is AuthenticationEvent.UpdateUI -> {
                        printError("Reached")
                        updateUI(event.user)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (exception: ApiException) {
                printError("onActivityResult: Google sign in failed ${exception.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        authenticationViewModel.loadingState.value = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    authenticationViewModel.loadingState.value = false
                    updateUI(auth.currentUser)
                } else {
                    authenticationViewModel.loadingState.value = false
                    printError("signInWithCredential: failure ${task.exception}")
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            printError("updateUI: User not signed in")
            return
        }
        val mainScreenIntent = Intent(this, HomeActivity::class.java)
//        mainScreenIntent.putExtra("photo", user.photoUrl.toString())
//        mainScreenIntent.putExtra("name", user.displayName)
        startActivity(mainScreenIntent)
        finish()
    }
}