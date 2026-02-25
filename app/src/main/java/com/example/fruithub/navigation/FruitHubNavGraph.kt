package com.example.fruithub.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruithub.screens.authentication.AuthenticationScreen
import com.example.fruithub.screens.homepage.FruitSaladHomeScreen
import com.example.fruithub.screens.splash.SplashScreen
import com.example.fruithub.screens.welcome.WelcomeScreen
import com.example.fruithub.screens.basket.BasketScreen
import com.example.fruithub.screens.completeorder.OrderSuccessScreen
import com.example.fruithub.screens.productdetails.ProductDetailsScreen

@Composable
fun FruitHubNavGraph() {
    val navController = rememberNavController()

    // وضع الـ Scaffold هنا يحمي الشاشات من التداخل مع شريط الحالة أو أزرار النظام
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.CompleteOrder.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. Splash
            composable(Screen.Splash.route) {
                SplashScreen(onTimeout = {
                    navController.navigate(Screen.Welcome.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
            }

            // 2. Welcome
            composable(Screen.Welcome.route) {
                WelcomeScreen(onContinueClick = {
                    navController.navigate(Screen.Authentication.route)
                })
            }

            // 3. Authentication
            composable(Screen.Authentication.route) {
                AuthenticationScreen(onLoginSuccess = { name ->
                    navController.navigate(Screen.Home.createRoute(name)) {
                        popUpTo(Screen.Authentication.route) { inclusive = true }
                    }
                })
            }

            // 4. Home
            composable(Screen.Home.route) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("userName") ?: "User"
                FruitSaladHomeScreen(
                    userName = name,
                    onBasketClick = { navController.navigate(Screen.Basket.route) },
                    onProductClick = { navController.navigate(Screen.ProductDetails.route) })
            }

            // 5. Product Details
            composable(Screen.ProductDetails.route) {
                ProductDetailsScreen(
                    onBackClick = { navController.popBackStack() },
                    onAddToBasketClick = {
                        // الانتقال إلى شاشة السلة
                        navController.navigate(Screen.Basket.route)
                    })
            }

            // 6. Basket
            composable(Screen.Basket.route) {
                BasketScreen(onBackClick = { navController.popBackStack() }, onNavigateToSuccess = {
                    // الانتقال إلى شاشة النجاح
                    navController.navigate(Screen.CompleteOrder.route) {
                        // مسح شاشة السلة من المكدس (Backstack) حتى لا يعود المستخدم إليها عند الضغط على زر الرجوع
                        popUpTo(Screen.Basket.route) { inclusive = true }
                    }
                })
            }

            // 7. OrderSuccess
            composable(Screen.CompleteOrder.route) {
                OrderSuccessScreen(onTrackOrderClick = {
                    // يمكنك إضافة شاشة تتبع الطلب لاحقاً هنا
                }, onContinueShoppingClick = {
                    // العودة إلى الصفحة الرئيسية عند الضغط على "مواصلة التسوق"
                    navController.navigate(Screen.Home.createRoute("User")) {
                        // مسح الشاشات السابقة ليعود وكأنه دخل التطبيق حديثاً للصفحة الرئيسية
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                })
            }
        }

    }
}