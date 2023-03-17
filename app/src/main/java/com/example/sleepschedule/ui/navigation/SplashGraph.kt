package com.example.sleepschedule.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sleepschedule.ui.screens.splash.SplashRoute

fun NavGraphBuilder.splashGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screens.Splash.route,
        route = Graphs.Splash.route
    ) {
        composable(
            route = Screens.Splash.route
        ) {
            SplashRoute(
                splashViewModel = hiltViewModel(),
                onUserLogAction = { loggedIn ->
                    val route = if (loggedIn) Screens.Home.route else Graphs.Login.route
                    navController.navigate(route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                }
            )
        }
    }
}