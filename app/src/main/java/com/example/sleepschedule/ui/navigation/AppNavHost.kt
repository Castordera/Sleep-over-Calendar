package com.example.sleepschedule.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.features.event.add.ui.ScheduleDetailRoute
import com.ulises.features.events.list.ui.ScheduleListRoute
import com.ulises.navigation.Graphs
import com.ulises.navigation.Screens
import com.ulises.user.detail.ui.UserRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: SleepScheduleAppState,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Graphs.Splash.route
    ) {
        splashGraph(navController = navController)
        loginGraph(
            appState = appState,
            navController = navController,
        )
        composable(
            route = Screens.Home.route
        ) {
            ScheduleListRoute(
                onNavigateToAdd = { navController.navigate(Screens.AddEvent.route) }
            )
        }
        composable(
            route = Screens.AddEvent.route,
        ) {
            ScheduleDetailRoute(
                onNavigateBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screens.User.route
        ) {
            UserRoute(
                onLoggedOut = {
                    navController.navigate(Graphs.Login.route) {
                        popUpTo(Screens.User.route) { inclusive = true }
                    }
                }
            )
        }
    }
}