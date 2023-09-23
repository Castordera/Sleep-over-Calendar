package com.example.sleepschedule.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ulises.navigation.bottomNavigationScreens
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(navController, coroutineScope) {
    SleepScheduleAppState(navController, coroutineScope, snackBarHostState)
}

@Stable
class SleepScheduleAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val snackBarHostState: SnackbarHostState
) {
    private val bottomBarRoutes = bottomNavigationScreens.map { it.route }

    val shouldShowBottomBar: Boolean
    @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    val currentDestination: NavDestination?
    @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigateToBottomBarRoute(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(navController.currentDestination?.route ?: "") {
                saveState = true
                inclusive = true
            }
        }
    }
}