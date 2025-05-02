package com.example.sleepschedule.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ulises.features.splash.ui.SplashRoute
import com.ulises.navigation.Graph
import com.ulises.navigation.Screens

fun NavGraphBuilder.splashGraph(
    navController: NavHostController
) {
    navigation<Graph.Splash>(startDestination = Screens.Splash) {
        composable<Screens.Splash> {
            SplashRoute(
                onUserLogAction = { loggedIn ->
                    if (loggedIn) {
                        navController.navigate(Screens.Home) {
                            popUpTo(Screens.Splash) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Graph.Login) {
                            popUpTo(Screens.Splash) { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}