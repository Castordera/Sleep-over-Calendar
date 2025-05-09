package com.example.sleepschedule.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.example.sleepschedule.ui.components.SleepBottomNavigation
import com.example.sleepschedule.ui.rememberAppState
import com.ulises.navigation.bottomNavigationScreens

@Composable
fun MainScreensNavigation(
    appState: SleepScheduleAppState = rememberAppState()
) {
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = appState.shouldShowBottomBar,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                SleepBottomNavigation(
                    appState = appState,
                    items = bottomNavigationScreens,
                )
            }
        },
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom),
    ) { padding ->
        AppNavHost(
            modifier = Modifier.padding(padding),
            appState = appState,
            navController = appState.navController
        )
    }
}