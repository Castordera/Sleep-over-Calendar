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
import com.example.sleepschedule.ui.screens.login.LoginRoute
import com.example.sleepschedule.ui.screens.signin.SignInRoute
import com.example.sleepschedule.ui.screens.splash.SplashRoute
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
            route = Screens.Splash.route
        ) {
            SplashRoute(
                splashViewModel = hiltViewModel(),
                onUserLogAction = { loggedIn ->
                    val route = if (loggedIn) Screens.Home.route else Screens.Login.route
                    navController.navigate(route) {
                        popUpTo(Screens.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screens.Login.route
        ) {
            LoginRoute(
                loginViewModel = hiltViewModel(),
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
        composable(
            route = Screens.SignIn.route
        ) {
            SignInRoute(
                registerViewModel = hiltViewModel(),
                navigateToHome = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.SignIn.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = Screens.Home.route
        ) {
            MainScheduleScreen(
                scheduleViewModel = hiltViewModel(),
                onNavigateToAdd = { navController.navigate(Screens.AddEvent.route) }
            )
        }
        composable(
            route = Screens.AddEvent.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            }
        ) {
            ScheduleDetailRoute(
                viewModel = hiltViewModel(),
                onNavigateBackClick = { navController.popBackStack() }
            )
        }
    }
}