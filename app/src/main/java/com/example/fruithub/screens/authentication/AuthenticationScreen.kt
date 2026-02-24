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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fruithub.commonComponent.ButtonOrange
import com.example.fruithub.ui.theme.OrangePrimary
import com.example.fruithub.R
import com.example.fruithub.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun AuthenticationScreen(
    viewModel: AuthenticationViewModel = viewModel(), // استخدام ViewModel
    onLoginSuccess: (String) -> Unit // تمرير دالة بدلاً من NavController
) {
    // منطق التحريك (الأنميشن) يبقى هنا لأنه خاص بالـ UI فقط
    var startBasketAnimation by remember { mutableStateOf(false) }
    var startTextAnimation by remember { mutableStateOf(false) }
    var startButtonAnimation by remember { mutableStateOf(false) }

    val basketScale by animateFloatAsState(
        targetValue = if (startBasketAnimation) 1f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    )

    LaunchedEffect(Unit) {
        delay(200)
        startBasketAnimation = true
        delay(700)
        startTextAnimation = true
        delay(400)
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
                        value = viewModel.userName,
                        onValueChange = { viewModel.updateUserName(it) },
                        placeholder = {
                            Text(
                                "Tony", color = Color.Gray
                            )
                        }, // جعل الـ Placeholder باهتًا
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color(0xFF27214D), // 👈 هذا هو اللون الغامق المطلوب للنص عند الكتابة
                            fontSize = 16.sp, fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color(0xFF27214D),   // 👈 لضمان اللون عند التركيز
                            unfocusedTextColor = Color(0xFF27214D), // 👈 لضمان اللون بعد انتهاء التركيز
                            unfocusedContainerColor = Color(0xFFF3F3F3),
                            focusedContainerColor = Color(0xFFF3F3F3),
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
                    AnimatedVisibility(visible = startButtonAnimation) {
                        ButtonOrange(
                            onClick = {
                                if (viewModel.isInputValid()) {
                                    onLoginSuccess(viewModel.userName)
                                }
                            }, modifier = Modifier
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
}