package com.example.sleepschedule.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.example.sleepschedule.ui.navigation.Screens

@Composable
fun SleepBottomNavigation(
    appState: SleepScheduleAppState,
    items: List<Screens>
) {
    BottomNavigation {
        items.forEach { screen ->
            BottomNavigationItem(
                selected = appState.currentDestination?.route == screen.route,
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon!!),
                        contentDescription = null
                    )
                },
                label = { Text(screen.name.orEmpty()) },
                alwaysShowLabel = true,
                onClick = { appState.navigateToBottomBarRoute(screen.route) }
            )
        }
    }
}