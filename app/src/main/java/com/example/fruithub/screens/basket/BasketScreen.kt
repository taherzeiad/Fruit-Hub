package com.example.fruithub.screens.basket

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.fruithub.commonComponent.CheckoutDialogContent

// كائن البيانات للسلة
data class BasketItem(
    val name: String, val imageRes: Int, val bgColor: Color, val price: String, val quantity: String
)

@Composable
fun BasketScreen(onBackClick: () -> Unit, onNavigateToSuccess: () -> Unit) {
    var startAnimations by remember { mutableStateOf(false) }

    // الحالة الخاصة بإظهار الـ BottomSheet
    var showCheckoutSheet by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LaunchedEffect(Unit) {
        startAnimations = true
    }

    val headerHeight by animateDpAsState(
        targetValue = if (startAnimations) 110.dp else screenHeight,
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
        label = "HeaderHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- 1. طبقة المحتوى ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                val items = listOf(
                    BasketItem(
                        "Quinoa fruit salad",
                        R.drawable.quinoa_salad,
                        Color(0xFFFFFAEB),
                        "20,000",
                        "2 packs"
                    ), BasketItem(
                        "Melon fruit salad",
                        R.drawable.melon_salad,
                        Color(0xFFF3F4F9),
                        "20,000",
                        "2 packs"
                    ), BasketItem(
                        "Tropical fruit salad",
                        R.drawable.tropical_salad,
                        Color(0xFFFFF2F2),
                        "20,000",
                        "2 packs"
                    )
                )

                items.forEachIndexed { index, item ->
                    val isLeftToRight = index % 2 == 0
                    AnimatedVisibility(
                        visible = startAnimations && headerHeight < 400.dp,
                        enter = slideInHorizontally(
                            initialOffsetX = { if (isLeftToRight) -it else it },
                            animationSpec = tween(1000, delayMillis = 500 + (index * 150))
                        ) + fadeIn(tween(800))
                    ) {
                        Column {
                            BasketRow(item = item)
                            Divider(
                                modifier = Modifier.padding(vertical = 16.dp),
                                color = Color(0xFFF3F3F3)
                            )
                        }
                    }
                }
            }

            // --- الجزء السفلي (السعر وزر Checkout) ---
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
                        onClick = { showCheckoutSheet = true }, // تفعيل ظهور الـ BottomSheet
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

        // --- 2. طبقة الهيدر البرتقالي ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(SecondaryColor)
                .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            if (headerHeight < 250.dp) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    BackButton(
                        onBackClick = onBackClick, modifier = Modifier.padding(bottom = 10.dp)
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

        if (showCheckoutSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { showCheckoutSheet = false })
        }

        AnimatedVisibility(
            visible = showCheckoutSheet,
            enter = slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(
                    1500, easing = FastOutSlowInEasing
                )
            ) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(800)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
            ) {
                CheckoutDialogContent(onDismiss = { showCheckoutSheet = false }, onPayOnDelivery = {
                    showCheckoutSheet = false
                    onNavigateToSuccess()
                }, onPayWithCard = { /*logic*/ })
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