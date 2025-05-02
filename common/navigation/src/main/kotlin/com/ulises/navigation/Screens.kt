package com.ulises.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screens {
    @Serializable
    data object Home : Screens

    @Serializable
    data object AddItem : Screens

    @Serializable
    data object Splash : Screens

    @Serializable
    data object Login : Screens

    @Serializable
    data object SignIn : Screens

    @Serializable
    data object User : Screens
}

@Serializable
sealed interface Graph {
    @Serializable
    data object Splash : Graph

    @Serializable
    data object Login : Graph
}

data class BottomScreen(
    val name: String,
    val route: Screens,
    val icon: ImageVector,
)

val bottomNavigationScreens = listOf(
    BottomScreen("Home", Screens.Home, Icons.Default.Home),
    BottomScreen("User", Screens.User, Icons.Default.Person),
)