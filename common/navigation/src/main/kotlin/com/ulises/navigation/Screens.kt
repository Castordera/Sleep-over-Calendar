package com.ulises.navigation

import com.ulises.common.navigation.R

sealed class Screens(
    val route: String,
    val name: String? = null,
    val icon: Int? = null
) {
    data object Splash : Screens("splash")
    data object Login : Screens("login")
    data object SignIn : Screens("sign_in")
    data object Home : Screens("home", "Home", R.drawable.ic_home)
    data object AddEvent : Screens("add_event")
    data object User : Screens("user", "User", R.drawable.ic_person)
}



val bottomNavigationScreens = listOf(
    Screens.Home,
    Screens.User
)