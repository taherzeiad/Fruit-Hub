package com.example.fruithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.fruithub.ui.theme.FruitHubTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fruithub.screens.authentication.AuthenticationScreen
import com.example.fruithub.screens.homepage.FruitSaladHomeScreen
import com.example.fruithub.screens.splash.SplashScreen
import com.example.fruithub.screens.welcome.WelcomeScreen
import com.example.fruithub.screens.basket.BasketScreen // تأكد من إنشاء هذا الملف
import com.example.fruithub.screens.productdetails.ProductDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FruitHubTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "productdetails",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash") {
                            SplashScreen(onTimeout = {
                                navController.navigate("welcome") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            })
                        }

                        composable("welcome") {
                            WelcomeScreen(navController = navController)
                        }

                        composable("authentication") {
                            AuthenticationScreen(navController = navController)
                        }

                        composable("home/{userName}") { backStackEntry ->
                            val name = backStackEntry.arguments?.getString("userName") ?: "User"
                            FruitSaladHomeScreen(
                                userName = name,
                                onBasketClick = { navController.navigate("basket") })
                        }
                        composable("productdetails") {
                            ProductDetailsScreen(onBackClick = { navController.popBackStack() })
                        }
                        composable("basket") {
                            BasketScreen(onBackClick = { navController.popBackStack() })
                        }

                    }
                }
            }
        }
    }
}