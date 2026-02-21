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
import com.example.fruithub.screens.welcome.WelcomeScreen // تأكد من صحة مسار الـ package

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
                        // غير هذا السطر للواجهة التي تريد العمل عليها
                        startDestination = "home/Mohamed", // مثال: تشغيل الهوم مباشرة
                        // startDestination = "welcome" // مثال: تشغيل مرحباً
                        // startDestination = "authentication" // مثال: تشغيل المصادقة
                        // startDestination = "splash" // الأصلية
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
                            FruitSaladHomeScreen(userName = name)
                        }
                    }
                }
            }
        }

    }
}