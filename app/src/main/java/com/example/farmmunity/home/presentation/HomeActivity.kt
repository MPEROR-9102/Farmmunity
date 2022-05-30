package com.example.farmmunity.home.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.farmmunity.authentication.AuthenticationActivity
import com.example.farmmunity.home.core.HomeConstants
import com.example.farmmunity.home.presentation.add_question.AddQuestionViewModel
import com.example.farmmunity.home.presentation.navigation.HomeNavGraph
import com.example.farmmunity.home.presentation.questions.QuestionsViewModel
import com.example.farmmunity.ui.theme.FarmmunityTheme
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var auth: FirebaseAuth
    private val questionViewModel: QuestionsViewModel by viewModels()
    private val addQuestionViewModel: AddQuestionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmmunityTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HomeNavGraph(
                        navHostController = rememberNavController(),
                        questionViewModel = questionViewModel,
                        addQuestionViewModel = addQuestionViewModel
                    )
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            questionViewModel.homeEventFlow.collectLatest { event ->
                if (event is HomeEvent.ShowAuthenticationScreen) {
                    auth.signOut()
                    startActivity(
                        Intent(
                            this@HomeActivity,
                            AuthenticationActivity::class.java
                        )
                    )
                    this@HomeActivity.finish()
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            addQuestionViewModel.homeEventFlow.collectLatest { event ->
                if (event is HomeEvent.OnGalleryClicked) {
                    Intent(Intent.ACTION_GET_CONTENT).also {
                        it.type = "image/*"
                        startActivityForResult(it, HomeConstants.RC_IMAGE_PICK)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == HomeConstants.RC_IMAGE_PICK) {
            data?.data?.let {
                addQuestionViewModel.uploadUrl.value = it
            }
        }
    }
}