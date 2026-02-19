package com.example.fruithub.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.fruithub.commonComponent.ButtonOrange
import com.example.fruithub.ui.theme.OrangePrimary

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // الجزء العلوي: الخلفية البرتقالية مع الصور
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f) // تقليل الوزن قليلاً لإعطاء مساحة أكبر للنص
                .background(OrangePrimary)
        ) {

            // 1. صورة الفاكهة الصغيرة في أعلى اليمين (بتعديل المسافة)
            Image(
                painter = painterResource(id = R.drawable.smallfruit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 40.dp, end = 30.dp) // تعديل المسافات العلوية والجانبية
                    .size(45.dp)
            )

            // 2. مجموعة سلة الفواكه والظل في المنتصف
            Column(
                modifier = Modifier
                    .align(Alignment.Center) // تغيير من BottomCenter إلى Center
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // صورة سلة الفواكه
                Image(
                    painter = painterResource(id = R.drawable.fruit_basket),
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
                    painter = painterResource(id = R.drawable.basket_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(280.dp)
                        .height(14.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(38.dp))   // 👈 هذه تضيف مسافة بين البرتقالي والنص

        // الجزء السفلي: النصوص والزر
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f) // تعديل الوزن
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 24.dp), // تعديل الباديينغ
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween // توزيع المسافات بين العناصر
        ) {
            // حاوية النصوص
            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Get The Freshest Fruit Salad Combo",
                    fontSize = 20.sp, // تكبير الخط قليلاً
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27214D),
                    textAlign = TextAlign.Start,
                    lineHeight = 32.sp // تحسين قراءة النص
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We deliver the best and freshest fruit salad in town. Order for a combo today!!!",
                    fontSize = 16.sp,
                    color = Color(0xFF5D577E),
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Start
                )
            }

            // الزر في الأسفل
            ButtonOrange(
                onClick = { navController.navigate("authentication") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Let’s Continue",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}