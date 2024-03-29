package com.example.sleepschedule.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ulises.features.splash.ui.SplashRoute
import com.ulises.navigation.Graphs
import com.ulises.navigation.Screens

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