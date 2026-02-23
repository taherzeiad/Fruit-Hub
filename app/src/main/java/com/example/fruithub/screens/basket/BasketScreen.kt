package com.example.fruithub.screens.basket

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun BasketScreen(onBackClick: () -> Unit) {
    // 1. زيادة مدة الأنميشن والتحكم في الحالة
    var startAnimations by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // تأخير بسيط جداً قبل البدء لضمان استقرار الشاشة
        startAnimations = true
    }

    // 2. تعديل أنيميشن الارتفاع (تصغير الجزء البرتقالي)
    val headerHeight by animateDpAsState(
        // جعل الارتفاع الابتدائي 1000dp لضمان تغطية الشاشة بالكامل (أو استخدام LocalConfiguration)
        targetValue = if (startAnimations) 110.dp else 1000.dp, animationSpec = tween(
            durationMillis = 1500, // 👈 تم زيادة المدة لثانية ونصف لجعل الحركة أبطأ وأوضح
            easing = FastOutSlowInEasing
        ), label = "HeaderHeight"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- الهيدر البرتقالي ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight) // الارتفاع المتغير
                .background(SecondaryColor)
                .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // تظهر المحتويات فقط عندما يقترب الهيدر من حجمه النهائي
            androidx.compose.animation.AnimatedVisibility(
                visible = startAnimations && headerHeight < 300.dp,
                enter = fadeIn(tween(800)) + slideInVertically { it / 2 }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    // زر الرجوع
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .clickable { onBackClick() }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.backicon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Go back", fontSize = 12.sp, color = Color.Black)
                    }

                    Spacer(modifier = Modifier.weight(0.3f))
                    Text(
                        text = "My Basket",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // --- القائمة بتأخير يتناسب مع حركة الهيدر ---
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            val items = listOf(
                Triple("Quinoa fruit salad", R.drawable.quinoa_salad, Color(0xFFFFFAEB)),
                Triple("Melon fruit salad", R.drawable.melon_salad, Color(0xFFF3F4F9)),
                Triple("Tropical fruit salad", R.drawable.tropical_salad, Color(0xFFFFF2F2))
            )

            items.forEachIndexed { index, item ->
                val isLeftToRight = index % 2 == 0

                AnimatedVisibility(
                    visible = startAnimations && headerHeight < 400.dp, // تبدأ بعد تقلص الهيدر قليلاً
                    enter = slideInHorizontally(
                        initialOffsetX = { if (isLeftToRight) -it else it }, animationSpec = tween(
                            durationMillis = 1000, // 👈 جعل حركة العناصر أبطأ أيضاً
                            delayMillis = 500 + (index * 200) // تأخير إضافي ليتناسب مع حركة الهيدر
                        )
                    ) + fadeIn(tween(800))
                ) {
                    Column {
                        BasketRow(item.first, "2packs", "20,000", item.second, item.third)
                        Divider(
                            modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF3F3F3)
                        )
                    }
                }
            }
        }

        // --- الجزء السفلي بحركة بطيئة ---
        AnimatedVisibility(
            visible = startAnimations && headerHeight < 300.dp, enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(durationMillis = 1200, delayMillis = 1000)
            ) + fadeIn()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF27214D)
                    )
                    Text(
                        "₦ 60,000",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF27214D)
                    )
                }

                Button(
                    onClick = { /* logic */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA451)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(199.dp)
                        .height(56.dp)
                ) {
                    Text("Checkout", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun BasketRow(name: String, quantity: String, price: String, imgRes: Int, bgColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        // صورة المنتج بخلفية ملونة
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(bgColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // النصوص
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF27214D)
            )
            Text(text = quantity, fontSize = 14.sp, color = Color.Gray)
        }

        // السعر
        Text(
            text = "₦ $price",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF27214D)
        )
    }
}