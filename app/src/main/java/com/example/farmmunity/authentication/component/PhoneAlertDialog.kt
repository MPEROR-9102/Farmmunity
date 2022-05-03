package com.example.farmmunity.authentication.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.farmmunity.authentication.AuthenticationViewModel
import com.example.farmmunity.authentication.core.AuthenticationConstants.DISMISS
import com.example.farmmunity.authentication.core.AuthenticationConstants.PHONE_TEXT
import com.example.farmmunity.authentication.core.AuthenticationConstants.SEND
import com.example.farmmunity.authentication.core.InvalidPhoneException
import com.example.farmmunity.authentication.screen.PhoneEvent
import kotlinx.coroutines.job

@Composable
fun PhoneAlertDialog(
    authenticationViewModel: AuthenticationViewModel
) {

    val (phone, onPhoneChange) = remember { mutableStateOf("") }
    var invalidPhone by remember {
        mutableStateOf(false)
    }
    val focusRequester = FocusRequester()

    if (authenticationViewModel.openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                authenticationViewModel.openDialog.value = false
            },
            title = {},
            text = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "+91",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    TextField(
                        value = phone,
                        onValueChange = onPhoneChange,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                        },
                        placeholder = {
                            Text(text = if (invalidPhone) "Invalid $PHONE_TEXT" else PHONE_TEXT)
                        },
                        modifier = Modifier.focusRequester(focusRequester),
                        isError = invalidPhone
                    )
                    LaunchedEffect(Unit) {
                        coroutineContext.job.invokeOnCompletion {
                            focusRequester.requestFocus()
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    try {
                        authenticationViewModel.openDialog.value = false
                        authenticationViewModel.onPhoneEvent(PhoneEvent.PhoneReceived(phone))
                    } catch (exception: InvalidPhoneException) {
                        authenticationViewModel.openDialog.value = true
                        onPhoneChange("")
                        invalidPhone = true
                    }
                }) {
                    Text(text = SEND)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    authenticationViewModel.openDialog.value = false
                }) {
                    Text(text = DISMISS)
                }
            }
        )
    }
}