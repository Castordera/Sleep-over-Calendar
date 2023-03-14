package com.example.sleepschedule.ui.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sleepschedule.ui.screens.MainScheduleScreen
import com.example.sleepschedule.ui.screens.ScheduleDetailRoute
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberAnimatedNavController(),
    startDestination: String
) {
    AnimatedNavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = NavDestinations.HOME
        ) {
            MainScheduleScreen(
                scheduleViewModel = hiltViewModel(),
                onNavigateToAdd = { navController.navigate(NavDestinations.ADD_DATE) }
            )
        }
        composable(
            route = NavDestinations.ADD_DATE,
            enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)) },
            popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(700)) }
        ) {
            ScheduleDetailRoute(
                viewModel = hiltViewModel(),
                onNavigateBackClick = { navController.popBackStack() }
            )
        }
    }
}