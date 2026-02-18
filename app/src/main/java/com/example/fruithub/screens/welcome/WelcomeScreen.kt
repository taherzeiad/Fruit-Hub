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
                .weight(1.2f) // زيادة الوزن قليلاً لتأخذ مساحة أكبر كالتصميم
                .background(OrangePrimary)
        ) {
            // 1. صورة الفاكهة الصغيرة في أعلى اليمين
            Image(
                painter = painterResource(id = R.drawable.smallfruit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 60.dp, end = 40.dp) // موازنة المكان
                    .size(40.dp)
            )

            // 2. سلة الفواكه والظل (مرتبين عمودياً داخل Box)
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fruit_basket),
                    contentDescription = "Fruit Basket",
                    modifier = Modifier
                        .width(301.dp)
                        .height(260.dp),
                    contentScale = ContentScale.Fit
                )

                // 3. الظل تحت السلة مباشرة
                Image(
                    painter = painterResource(id = R.drawable.basket_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(301.dp)
                        .height(12.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        // الجزء السفلي: النصوص والزر
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.Start, // النصوص في التصميم تبدأ من اليسار (أو حسب اللغة)
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Get The Freshest Fruit Salad Combo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF27214D),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "We deliver the best and freshest fruit salad in town. Order for a combo today!!!",
                fontSize = 16.sp,
                color = Color(0xFF5D577E),
                lineHeight = 24.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(1f)) // دفع الزر للأسفل

            // زر الاستمرار
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

            Spacer(modifier = Modifier.height(20.dp)) // مسافة أمان في الأسفل
        }
    }
}