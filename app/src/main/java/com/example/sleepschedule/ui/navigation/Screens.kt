package com.example.sleepschedule.ui.navigation

sealed class Screens(val route: String) {
    object Splash: Screens("splash")
    object Login: Screens("login")
    object SignIn: Screens("sign_in")
    object Home: Screens("home")
    object AddEvent: Screens("add_event")
}