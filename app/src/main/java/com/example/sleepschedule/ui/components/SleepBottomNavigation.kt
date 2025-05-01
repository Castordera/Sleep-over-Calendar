package com.example.sleepschedule.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.ulises.navigation.Screens

@Composable
fun SleepBottomNavigation(
    appState: SleepScheduleAppState,
    items: List<Screens>
) {
    BottomNav(
        items = items,
        currentDestination = appState.currentDestination?.route,
        navigateTo = appState::navigateToBottomBarRoute
    )
}

@Composable
private fun BottomNav(
    items: List<Screens>,
    currentDestination: String?,
    navigateTo: (String) -> Unit,
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination == screen.route,
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon!!),
                        contentDescription = null
                    )
                },
                alwaysShowLabel = false,
                onClick = { navigateTo(screen.route) }
            )
        }
    }
}

@Preview
@Composable
fun PrevNavigationBar() {
    BottomNav(
        items = listOf(Screens.Home, Screens.User),
        currentDestination = Screens.User.route,
        navigateTo = {},
    )
}