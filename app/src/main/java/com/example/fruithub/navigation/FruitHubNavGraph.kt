package com.example.fruithub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruithub.screens.authentication.AuthenticationScreen
import com.example.fruithub.screens.homepage.FruitSaladHomeScreen
import com.example.fruithub.screens.splash.SplashScreen
import com.example.fruithub.screens.welcome.WelcomeScreen
import com.example.fruithub.screens.basket.BasketScreen
import com.example.fruithub.screens.productdetails.ProductDetailsScreen

@Composable
fun FruitHubNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route // 👈 استخدام الـ Route من الكائن
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(onTimeout = {
                navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            })
        }

        composable(Screen.Welcome.route) {
            WelcomeScreen(navController)
        }

        composable(Screen.Authentication.route) {
            AuthenticationScreen(
                onLoginSuccess = { name ->
                    navController.navigate(Screen.Home.createRoute(name)) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                })
        }

        composable(Screen.Home.route) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("userName") ?: "User"
            FruitSaladHomeScreen(
                userName = name,
                onBasketClick = { navController.navigate(Screen.Basket.route) },
                onProductClick = { navController.navigate(Screen.ProductDetails.route) })
        }

        composable(Screen.Basket.route) {
            BasketScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.ProductDetails.route) {
            ProductDetailsScreen(onBackClick = { navController.popBackStack() })
        }
    }
}