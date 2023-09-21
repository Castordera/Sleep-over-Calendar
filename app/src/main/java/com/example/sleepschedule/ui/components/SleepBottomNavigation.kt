package com.example.sleepschedule.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.sleepschedule.ui.SleepScheduleAppState
import com.example.sleepschedule.ui.navigation.Screens

@Composable
fun SleepBottomNavigation(
    appState: SleepScheduleAppState,
    items: List<Screens>
) {
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = appState.currentDestination?.route == screen.route,
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon!!),
                        contentDescription = null
                    )
                },
                label = { Text(screen.name.orEmpty()) },
                alwaysShowLabel = false,
                onClick = { appState.navigateToBottomBarRoute(screen.route) }
            )
        }
    }
}

@Preview
@Composable
fun PrevNavigationBar() {
    SleepBottomNavigation(
        appState = SleepScheduleAppState(
            scaffoldState = rememberScaffoldState(),
            navController = rememberNavController(),
            coroutineScope = rememberCoroutineScope()
        ),
        items = listOf(
            Screens.Home,
            Screens.User
        )
    )
}