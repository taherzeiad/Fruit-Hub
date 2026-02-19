package com.example.fruithub.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruithub.commonComponent.ButtonOrange
import com.example.fruithub.ui.theme.OrangePrimary
import com.example.fruithub.R


@Composable
fun AuthenticationScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // الجزء العلوي: الخلفية البرتقالية مع الصور
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f) // تقليل الوزن قليلاً لإعطاء مساحة أكبر للنص
                .background(OrangePrimary)
        ) {

            // 1. صورة الفاكهة الصغيرة في أعلى اليمين (بتعديل المسافة)
            Image(
                painter = painterResource(id = R.drawable.smallfruit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 85.dp, end = 10.dp) // تعديل المسافات العلوية والجانبية
                    .size(45.dp)
            )

            // 2. مجموعة سلة الفواكه والظل في المنتصف
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)   // 👈 ينزل المحتوى للأسفل
                    .padding(bottom = 40.dp)         // 👈 تحكم دقيق بالمسافة
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // صورة سلة الفواكه
                Image(
                    painter = painterResource(id = R.drawable.kisspng),
                    contentDescription = "Fruit Basket",
                    modifier = Modifier
                        .width(320.dp)
                        .height(280.dp),
                    contentScale = ContentScale.Fit
                )

                // مسافة صغيرة بين السلة والظل
                Spacer(modifier = Modifier.height(4.dp))

                // صورة الظل (بتوسيطها تحت السلة مباشرة)
                Image(
                    painter = painterResource(id = R.drawable.shadow2),
                    contentDescription = null,
                    modifier = Modifier
                        .width(280.dp)
                        .height(14.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Spacer(modifier = Modifier.height(63.dp))



        // الجزء السفلي (الكارد الأبيض)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    Color(0xFFFFFFFF), shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "What is your firstname?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF27214D)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // حقل الاسم
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Tony") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFEDEDED),
                        focusedContainerColor = Color(0xFFEDEDED),
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    )
                )
            }
            Spacer(modifier = Modifier.height(35.dp))


            // نفس الزر السابق
            ButtonOrange(
                onClick = { /* navController.navigate("home") */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Start Ordering",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

        }
    }
}