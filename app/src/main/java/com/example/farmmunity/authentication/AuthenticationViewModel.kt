package com.example.farmmunity.authentication

import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.farmmunity.authentication.core.AuthenticationUtils.Companion.printError
import com.example.farmmunity.authentication.core.InvalidCodeException
import com.example.farmmunity.authentication.core.InvalidPhoneException
import com.example.farmmunity.authentication.core.PhoneAuthResult
import com.example.farmmunity.authentication.screen.PhoneEvent
import com.example.farmmunity.authentication.screen.VerifyEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    val loadingState = mutableStateOf(false)
    val openDialog = mutableStateOf(false)

    private val eventChannel = Channel<AuthenticationEvent>()
    val eventFlow = eventChannel.receiveAsFlow()

    private val _introEventFlow = MutableSharedFlow<IntroUIEvent>()
    val introEventFlow = _introEventFlow.asSharedFlow()

    val code = mutableStateOf("")
    private lateinit var verificationId: String

    @Throws(InvalidPhoneException::class)
    fun onPhoneEvent(authenticationEvent: PhoneEvent) {
        when (authenticationEvent) {
            is PhoneEvent.PhoneReceived -> {
                val phone = authenticationEvent.phone
                if (phone.length == 10 && phone.isDigitsOnly()) {
                    viewModelScope.launch {
                        _introEventFlow.emit(IntroUIEvent.ShowVerifyScreen("+91$phone"))
                    }
                } else {
                    throw InvalidPhoneException("Invalid Phone Number")
                }
            }
        }
    }

    @Throws(InvalidCodeException::class)
    fun onVerifyEvent(verifyEvent: VerifyEvent) {
        when (verifyEvent) {
            VerifyEvent.OnCodeSubmit -> {
                if (code.value.length == 6 && code.value.isDigitsOnly()) {
                    firebaseAuthWithPhone(
                        PhoneAuthProvider.getCredential(verificationId, code.value)
                    )
                } else {
                    throw InvalidCodeException("Invalid OTP")
                }
            }
        }
    }

    fun onGoogleSignInClicked() {
        viewModelScope.launch {
            eventChannel.send(AuthenticationEvent.SignInWithGoogle)
        }
    }

    fun onPhoneAuthResultReceived(phoneAuthResult: PhoneAuthResult) {
        when (phoneAuthResult) {
            is PhoneAuthResult.OnCodeSent -> {
                verificationId = phoneAuthResult.verificationId
            }
            is PhoneAuthResult.OnVerificationCompleted -> {
                phoneAuthResult.credential.apply {
                    loadingState.value = true
                    code.value = smsCode ?: ""
                    firebaseAuthWithPhone(phoneAuthResult.credential)
                }
            }
        }
    }

    private fun firebaseAuthWithPhone(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch {
                    eventChannel.send(AuthenticationEvent.UpdateUI(task.result.user))
                }
            } else {
                loadingState.value = false
                code.value = ""
                printError(task.exception?.localizedMessage ?: task.exception.toString())
            }
        }
    }

    sealed class IntroUIEvent {
        data class ShowVerifyScreen(val phone: String) : IntroUIEvent()
    }
}

