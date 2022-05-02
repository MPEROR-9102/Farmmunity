package com.example.farmmunity.authentication.navigation

import com.example.farmmunity.authentication.core.AuthenticationConstants

sealed class AuthenticationScreen(val route: String) {
    object IntroScreen : AuthenticationScreen(AuthenticationConstants.INTRO_SCREEN)
    object PhoneScreen : AuthenticationScreen(AuthenticationConstants.PHONE_SCREEN)
    object VerifyScreen : AuthenticationScreen(AuthenticationConstants.VERIFY_SCREEN)
}
