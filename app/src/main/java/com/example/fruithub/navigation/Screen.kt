package com.example.fruithub.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Welcome : Screen("welcome")
    object Authentication : Screen("authentication")
    object Basket : Screen("basket")
    object ProductDetails : Screen("product_details") // استخدمت underscore بدلاً من المسافة

    // الشاشات التي تحتوي على بارامترات
    object Home : Screen("home/{userName}") {
        fun createRoute(userName: String) = "home/$userName"
    }
}