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
import com.example.fruithub.screens.splash.SplashScreen
import com.example.fruithub.screens.welcome.WelcomeScreen // تأكد من صحة مسار الـ package

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FruitHubTheme {
                // إنشاء وحدة التحكم في التنقل
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // الـ NavHost هو المسؤول عن عرض الواجهة المناسبة بناءً على المسار (Route)
                    NavHost(
                        navController = navController,
                        startDestination = "splash", // نقطة البداية هي السبلاش
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // تعريف مسارات الواجهات
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
                            // سنضع واجهة الـ Auth هنا لاحقاً
                        }

                        // أضف باقي الواجهات هنا بنفس الطريقة
                    }
                }
            }
        }
    }
}