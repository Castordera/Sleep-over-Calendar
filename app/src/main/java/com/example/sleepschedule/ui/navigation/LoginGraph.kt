package com.example.sleepschedule.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.login.user.ui.LoginRoute
import com.ulises.navigation.Graphs
import com.ulises.navigation.Screens
import com.ulises.register.user.ui.SignInRoute

fun NavGraphBuilder.loginGraph(
    appState: SleepScheduleAppState,
    navController: NavHostController,
) {
    navigation(
        startDestination = Screens.Login.route,
        route = Graphs.Login.route
    ) {
        /** Login  */
        composable(
            route = Screens.Login.route
        ) {
            LoginRoute(
                snackBarHostState = appState.snackBarHostState,
                navigateTo = { dest ->
                    if (dest is Screens.SignIn) {
                        navController.navigate(dest.route)
                    } else {
                        navController.navigate(dest.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        /** SignIn  */
        composable(
            route = Screens.SignIn.route
        ) {
            SignInRoute(
                navigateToHome = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignIn.route) { inclusive = true }
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}