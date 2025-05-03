package com.example.sleepschedule.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.navigation.BottomScreen
import com.ulises.navigation.Screens

@Composable
fun SleepBottomNavigation(
    appState: SleepScheduleAppState,
    items: List<BottomScreen>
) {
    BottomNav(
        items = items,
        currentDestination = appState.currentDestination,
        navigateTo = appState::navigateToBottomBarRoute
    )
}

@Composable
private fun BottomNav(
    items: List<BottomScreen>,
    currentDestination: NavDestination?,
    navigateTo: (Screens) -> Unit,
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.hasRoute(screen.route::class) } == true,
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = null,
                    )
                },
                alwaysShowLabel = false,
                onClick = { navigateTo(screen.route) },
            )
        }
    }
}

@Preview
@Composable
fun PrevNavigationBar() {
    BottomNav(
        items = listOf(
            BottomScreen("Home", Screens.Home, Icons.Default.Home),
            BottomScreen("User", Screens.User, Icons.Default.Person),
        ),
        currentDestination = NavDestination("Home"),
        navigateTo = {},
    )
}