package com.example.farmmunity.authentication.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import com.example.farmmunity.authentication.AuthenticationViewModel
import com.example.farmmunity.authentication.core.AuthenticationConstants
import com.example.farmmunity.authentication.core.InvalidCodeException
import kotlinx.coroutines.job

@Composable
fun VerifyScreen(
    phone: String,
    authenticationViewModel: AuthenticationViewModel
) {

    val (code, onCodeChange) = authenticationViewModel.code
    var invalidCode by remember {
        mutableStateOf(false)
    }
    val focusRequester = FocusRequester()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = phone)
        OutlinedTextField(
            value = code,
            onValueChange = onCodeChange,
            placeholder = {
                Text(text = AuthenticationConstants.CODE)
            },
            isError = invalidCode,
            modifier = Modifier.focusRequester(focusRequester)
        )
        LaunchedEffect(key1 = true) {
            coroutineContext.job.invokeOnCompletion {
                focusRequester.requestFocus()
            }
        }
        if (authenticationViewModel.loadingState.value) {
            CircularProgressIndicator()
        }
        OutlinedButton(
            onClick = {
                try {
                    authenticationViewModel.loadingState.value = true
                    authenticationViewModel.onVerifyEvent(VerifyEvent.OnCodeSubmit)
                } catch (exception: InvalidCodeException) {
                    authenticationViewModel.loadingState.value = false
                    invalidCode = true
                }
            }
        ) {
            Text(text = AuthenticationConstants.SUBMIT)
        }
    }
}