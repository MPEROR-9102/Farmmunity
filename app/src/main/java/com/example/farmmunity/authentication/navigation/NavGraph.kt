package com.example.farmmunity.authentication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.farmmunity.authentication.intro.IntroScreen

@Composable
fun NavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = AuthenticationScreen.IntroScreen.route
    ) {
        composable(route = AuthenticationScreen.IntroScreen.route) {
            IntroScreen(navHostController)
        }
    }
}