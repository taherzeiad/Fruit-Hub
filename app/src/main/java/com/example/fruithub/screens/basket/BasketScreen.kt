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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.BackButton
import com.example.fruithub.ui.theme.SecondaryColor
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun BasketScreen(onBackClick: () -> Unit) {
    var startAnimations by remember { mutableStateOf(false) }

    // الحصول على ارتفاع الشاشة الفعلي لضمان التغطية الكاملة دون مبالغة
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(Unit) {
        startAnimations = true
    }

    // أنيميشن الارتفاع - أبطأ وأكثر سلاسة
    val headerHeight by animateDpAsState(
        targetValue = if (startAnimations) 110.dp else screenHeight, animationSpec = tween(
            durationMillis = 1500, easing = FastOutSlowInEasing
        ), label = "HeaderHeight"
    )

    // الحاوية الرئيسية (Box تضمن وضع العناصر فوق بعضها)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // --- 1. طبقة المحتوى (القائمة والجزء السفلي) ---
        // جعلناها في Column منفصل يبدأ بعد المسافة النهائية للهيدر
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp) // مساحة ثابتة للهيدر النهائي
        ) {
            // القائمة القابلة للتمرير
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

                items.forEachIndexed { index, tripleItem -> // غيرت الاسم لـ tripleItem للوضوح
                    val isLeftToRight = index % 2 == 0

                    // تحويل الـ Triple إلى كائن BasketItem
                    val basketItem = BasketItem(
                        name = tripleItem.first,
                        imageRes = tripleItem.second,
                        bgColor = tripleItem.third,
                        price = "20,000", // قيمة افتراضية أو يمكنك إضافتها للـ Triple
                        quantity = "2 packs"
                    )

                    AnimatedVisibility(
                        visible = startAnimations && headerHeight < 400.dp,
                        enter = slideInHorizontally(
                            initialOffsetX = { if (isLeftToRight) -it else it },
                            animationSpec = tween(1000, delayMillis = 500 + (index * 150))
                        ) + fadeIn(tween(800))
                    ) {
                        Column {
                            // الاستدعاء الصحيح الآن هو تمرير الكائن فقط
                            BasketRow(item = basketItem)

                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color(0xFFF3F3F3)
                            )
                        }
                    }
                }
            }

            // --- الجزء السفلي (السعر والزر) ---
            AnimatedVisibility(
                visible = startAnimations && headerHeight < 300.dp, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(1200, delayMillis = 800)
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
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
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

        // --- 2. طبقة الهيدر البرتقالي (توضع هنا لتكون فوق المحتوى Z-Index) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(SecondaryColor)
                .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            // تظهر العناصر فقط عندما يصغر الهيدر كفاية
            if (headerHeight < 250.dp) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    // زر الرجوع
                    BackButton(
                        onBackClick = { onBackClick() },
                        modifier = Modifier.padding(bottom = 10.dp) // يمكنك إضافة padding إضافي هنا
                    )

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
    }
}

@Composable
fun BasketRow(item: BasketItem) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(item.bgColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF27214D)
            )
            Text(text = item.quantity, fontSize = 14.sp, color = Color.Gray)
        }

        Text(
            text = "₦ ${item.price}",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF27214D)
        )
    }
}