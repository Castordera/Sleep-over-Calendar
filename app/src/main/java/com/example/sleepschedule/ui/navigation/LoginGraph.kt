package com.example.sleepschedule.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.login.user.ui.LoginRoute
import com.ulises.navigation.Graph
import com.ulises.navigation.Screens
import com.ulises.register.user.ui.SignInRoute

fun NavGraphBuilder.loginGraph(
    appState: SleepScheduleAppState,
    navController: NavHostController,
) {
    navigation<Graph.Login>(startDestination = Screens.Login) {
        /** Login  */
        composable<Screens.Login> {
            LoginRoute(
                snackBarHostState = appState.snackBarHostState,
                navigateTo = { dest ->
                    if (dest is Screens.SignIn) {
                        navController.navigate(dest)
                    } else {
                        navController.navigate(dest) {
                            popUpTo(Screens.Login) { inclusive = true }
                        }
                    }
                }
            )
        }
        /** SignIn  */
        composable<Screens.SignIn> {
            SignInRoute(
                navigateToHome = {
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.SignIn) { inclusive = true }
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}