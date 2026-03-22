package com.example.sleepschedule.ui.navigation

import androidx.compose.runtime.Composable
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.example.sleepschedule.ui.rememberAppState

@Composable
fun MainScreensNavigation(
    appState: SleepScheduleAppState = rememberAppState(),
) {
    AppNavHost(
        appState = appState,
        navController = appState.navController
    )
}