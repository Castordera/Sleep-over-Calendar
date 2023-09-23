package com.example.sleepschedule.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sleepschedule.ui.components.SleepBottomNavigation
import com.example.sleepschedule.ui.rememberAppState

@Composable
fun MainScreensNavigation() {
    val appState = rememberAppState()

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                SleepBottomNavigation(
                    appState = appState,
                    items = com.ulises.navigation.bottomNavigationScreens
                )
            }
        }
    ) { padding ->
        AppNavHost(
            modifier = Modifier.padding(padding),
            appState = appState,
            navController = appState.navController
        )
    }
}