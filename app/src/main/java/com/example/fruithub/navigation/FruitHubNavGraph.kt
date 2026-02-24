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
        navController = navController, startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(onTimeout = {
                navController.navigate("welcome") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("authentication") {
            AuthenticationScreen(navController)
        }

        composable("home/{userName}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("userName") ?: "User"
            FruitSaladHomeScreen(
                userName = name,
                onBasketClick = { navController.navigate("basket") },
                onProductClick = { navController.navigate("product details") } // 👈 أضف هذا السطر هنا
            )
        }

        composable("basket") {
            BasketScreen(onBackClick = { navController.popBackStack() })
        }
        composable("product details") {
            ProductDetailsScreen(onBackClick = { navController.popBackStack() })
        }
    }

}