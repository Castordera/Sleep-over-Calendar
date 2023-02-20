package com.example.sleepschedule.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sleepschedule.ui.screens.MainScheduleScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavDestinations.HOME) {
            MainScheduleScreen(
                scheduleViewModel = hiltViewModel(),
                onNavigateToAdd = { navController.navigate(NavDestinations.ADD_DATE) }
            )
        }
    }
}