package com.example.farmmunity.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.farmmunity.authentication.AuthenticationViewModel
import com.example.farmmunity.authentication.screen.IntroScreen
import com.example.farmmunity.authentication.screen.VerifyScreen
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun AuthenticationNavGraph(
    navHostController: NavHostController,
    authenticationViewModel: AuthenticationViewModel,
    phoneAuthOptionsBuilder: PhoneAuthOptions.Builder
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthenticationScreen.IntroScreen.route
    ) {
        composable(route = AuthenticationScreen.IntroScreen.route) {
            IntroScreen(
                navHostController = navHostController,
                authenticationViewModel = authenticationViewModel
            )
        }
        composable(
            route = AuthenticationScreen.VerifyScreen.route + "/{phone}",
            arguments = listOf(
                navArgument(name = "phone") {
                    type = NavType.StringType
                }
            )
        ) {
            val phone = it.arguments?.getString("phone") ?: ""
            PhoneAuthProvider.verifyPhoneNumber(
                phoneAuthOptionsBuilder.setPhoneNumber(phone).build()
            )
            VerifyScreen(
                phone = phone,
                authenticationViewModel = authenticationViewModel
            )
        }
    }
}