package com.example.fruithub.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruithub.R
import com.example.fruithub.ui.theme.OrangePrimary


@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // الجزء العلوي: الخلفية البرتقالية والصورة
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // يأخذ نصف الشاشة تقريباً
                .background(OrangePrimary), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background), // تأكد من اسم الصورة لديك
                contentDescription = "Fruit Basket",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )
        }

        // الجزء السفلي: النصوص والزر
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f) // الجزء المتبقي
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Get The Freshest Fruit Salad Combo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF27214D), // اللون الداكن للنص
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "We deliver the best and freshest fruit salad in town. Order for a combo today!!!",
                fontSize = 16.sp,
                color = Color(0xFF5D577E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // زر الاستمرار
            Button(
                onClick = {
                    navController.navigate("authentication")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Let’s Continue",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}