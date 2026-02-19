package com.example.fruithub.screens.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fruithub.commonComponent.ButtonOrange
import com.example.fruithub.ui.theme.OrangePrimary
import com.example.fruithub.R
import kotlinx.coroutines.delay

@Composable
fun AuthenticationScreen(navController: NavController) {
    // 1. حالات التحكم في بدء ظهور العناصر (States)
    var startBasketAnimation by remember { mutableStateOf(false) }
    var startTextAnimation by remember { mutableStateOf(false) }
    var startButtonAnimation by remember { mutableStateOf(false) }

    // 2. تعريف حركة التكبير (Scaling) للسلة والظل
    val basketScale by animateFloatAsState(
        targetValue = if (startBasketAnimation) 1f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, // تأثير ارتداد عند الوصول للحجم المطلوب
            stiffness = Spring.StiffnessLow
        ), label = "BasketScale"
    )

    // 3. ترتيب زمن ظهور العناصر (Sequence)
    LaunchedEffect(Unit) {
        delay(200) // انتظار بسيط جداً
        startBasketAnimation = true // تبدأ السلة بالنمو
        delay(700) // بعد نمو السلة، يظهر النص والحقل
        startTextAnimation = true
        delay(400) // أخيراً يظهر الزر
        startButtonAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // الجزء العلوي: الخلفية البرتقالية
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.2f)
                .background(OrangePrimary)
        ) {
            // صورة الفاكهة الصغيرة - تظل ظاهرة من البداية كما طلبت
            Image(
                painter = painterResource(id = R.drawable.smallfruit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 85.dp, end = 10.dp)
                    .size(45.dp)
            )

            // مجموعة سلة الفواكه والظل مع تأثير التكبير
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = basketScale, scaleY = basketScale
                    ), // 👈 تطبيق حركة التكبير هنا
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.kisspng),
                    contentDescription = "Fruit Basket",
                    modifier = Modifier
                        .width(301.dp)
                        .height(281.21.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(4.dp))

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

        // الجزء السفلي (النصوص والزر)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // تحريك النص والـ TextField من الأسفل للأعلى
            AnimatedVisibility(
                visible = startTextAnimation,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn()
            ) {
                Column {
                    Text(
                        text = "What is your firstname?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF27214D)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
            }

            Spacer(modifier = Modifier.height(35.dp))

            // تحريك الزر ليظهر بعد النصوص
            AnimatedVisibility(
                visible = startButtonAnimation,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                Column {
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
    }
}