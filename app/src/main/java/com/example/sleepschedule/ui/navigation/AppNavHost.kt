package com.example.sleepschedule.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.features.event.add.ui.ScheduleDetailRoute
import com.ulises.features.events.list.ui.ScheduleListRoute
import com.ulises.navigation.Graph
import com.ulises.navigation.Screens
import com.ulises.user.detail.ui.UserRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    appState: SleepScheduleAppState,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Graph.Splash,
    ) {
        splashGraph(navController = navController)
        loginGraph(
            appState = appState,
            navController = navController,
        )
        composable<Screens.Home> {
            ScheduleListRoute(
                onNavigateToAdd = { navController.navigate(Screens.AddItem(null)) },
                onGoToDetail = { navController.navigate(Screens.AddItem(it.id)) },
            )
        }
        composable<Screens.AddItem> {
            ScheduleDetailRoute(
                onNavigateBackClick = { navController.popBackStack() }
            )
        }
        composable<Screens.User> {
            UserRoute(
                onLoggedOut = {
                    navController.navigate(Graph.Login) {
                        popUpTo(Screens.User) { inclusive = true }
                    }
                }
            )
        }
    }
}