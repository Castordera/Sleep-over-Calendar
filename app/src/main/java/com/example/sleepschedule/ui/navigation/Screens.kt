package com.example.sleepschedule.ui.navigation

import com.example.sleepschedule.R

sealed class Screens(val route: String, val name: String? = null, val icon: Int? = null) {
    object Splash : Screens("splash")
    object Login : Screens("login")
    object SignIn : Screens("sign_in")
    object Home : Screens("home", "Home", R.drawable.ic_home)
    object AddEvent : Screens("add_event")
    object User : Screens("user", "User", R.drawable.ic_person)
}

sealed class Graphs(val route: String) {
    object Login : Graphs("loin-graph")
    object Splash : Graphs("splash-graph")
}

val bottomNavigationScreens = listOf(
    Screens.Home,
    Screens.User
)