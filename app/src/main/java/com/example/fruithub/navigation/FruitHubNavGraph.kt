package com.example.fruithub.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruithub.screens.splash.SplashScreen
import com.example.fruithub.screens.welcome.WelcomeScreen

@Composable
fun FruitHubNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "splash" // نقطة البداية
    ) {
        // واجهة السبلاش
        composable("splash") {
            SplashScreen(onTimeout = {
                // الانتقال إلى واجهة الترحيب وحذف السبلاش من الـ Backstack
                navController.navigate("welcome") {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }

        // واجهة الترحيب (Welcome Screen)
        composable("welcome") {
            WelcomeScreen(navController) // سنقوم ببرمجتها لاحقاً
        }
    }
}