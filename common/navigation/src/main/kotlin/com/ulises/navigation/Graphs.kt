package com.ulises.navigation

sealed class Graphs(
    val route: String
) {
    data object Login : Graphs("loin-graph")
    data object Splash : Graphs("splash-graph")
}