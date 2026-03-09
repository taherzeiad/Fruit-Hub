package com.example.fruithub.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Authentication : Screen("authentication")
    object Basket : Screen("basket")
    object ProductDetails : Screen("product_details")
    object CompleteOrderScreen : Screen("complete_order")
    object TrackOrder : Screen("track_order")


    object Home : Screen("home/{userName}") {
        fun createRoute(userName: String) = "home/$userName"
    }
}