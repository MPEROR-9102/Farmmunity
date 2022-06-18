package com.example.farmmunity.authentication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.farmmunity.R
import com.example.farmmunity.authentication.AuthenticationViewModel
import com.example.farmmunity.authentication.component.GoogleButton
import com.example.farmmunity.authentication.component.PhoneAlertDialog
import com.example.farmmunity.authentication.component.PhoneButton
import com.example.farmmunity.authentication.component.ProgressIndicator
import com.example.farmmunity.authentication.core.AuthenticationUtils.Companion.printError
import com.example.farmmunity.authentication.navigation.AuthenticationScreen
import com.example.farmmunity.core.AppConstants
import com.example.farmmunity.ui.theme.Brown
import kotlinx.coroutines.flow.collectLatest

@Composable
fun IntroScreen(
    navHostController: NavHostController,
    authenticationViewModel: AuthenticationViewModel
) {

    val lobsterFontFamily = FontFamily(
        Font(R.font.lobster_regular, FontWeight.Normal)
    )

    LaunchedEffect(key1 = true) {
        authenticationViewModel.introEventFlow.collectLatest { uiEvent ->
            when (uiEvent) {
                is AuthenticationViewModel.IntroUIEvent.ShowVerifyScreen -> {
                    navHostController.navigate(
                        AuthenticationScreen.VerifyScreen.route + "/${uiEvent.phone}"
                    ) {
                        popUpTo(AuthenticationScreen.IntroScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brown)
            .padding(horizontal = 16.dp)
            .padding(top = 160.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = AppConstants.APP_NAME,
            style = MaterialTheme.typography.h2.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            ),
            fontFamily = lobsterFontFamily
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight(.5f)
                .fillMaxWidth()
        ) {
            GoogleButton(
                modifier = Modifier.fillMaxWidth(.8f)
            ) {
                authenticationViewModel.onGoogleSignInClicked()
            }
            Spacer(modifier = Modifier.height(32.dp))
            PhoneButton(
                modifier = Modifier.fillMaxWidth(.8f)
            ) {
                printError("Reach 1")
                authenticationViewModel.openDialog.value = true
            }
        }
    }

    if (authenticationViewModel.openDialog.value) {
        PhoneAlertDialog(authenticationViewModel)
    }
    if (authenticationViewModel.loadingState.value) {
        ProgressIndicator()
    }
}


//context.startActivity(Intent(context, HomeActivity::class.java))
//(context as Activity).finish()