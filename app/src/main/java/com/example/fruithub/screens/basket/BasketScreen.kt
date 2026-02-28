package com.example.fruithub.screens.basket

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import com.example.fruithub.commonComponent.OrangeHeader
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.PrimaryColor

// كائن البيانات للسلة
data class BasketItem(
    val name: String, val imageRes: Int, val bgColor: Color, val price: String, val quantity: String
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BasketScreen(onBackClick: () -> Unit, onNavigateToSuccess: () -> Unit) {
    var startAnimations by remember { mutableStateOf(false) }

    var showCheckoutSheet by remember { mutableStateOf(false) }
    var showCardDetails by remember { mutableStateOf(false) }

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
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
                        )
                        Text(
                            "₦ 60,000",
                            fontSize = 22.sp,
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
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
                        Text(
                            "Checkout",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        OrangeHeader(
            title = "My Basket", headerHeight = headerHeight, onBackClick = onBackClick
        )

        if (showCheckoutSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { showCheckoutSheet = false })
        }

        AnimatedVisibility(
            visible = showCheckoutSheet && !showCardDetails,
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
                }, onPayWithCard = { showCardDetails = true })
            }
        }
        // 2. ديلاوج بيانات البطاقة
        AnimatedVisibility(
            visible = showCheckoutSheet && showCardDetails,
            enter = slideInVertically(
                initialOffsetY = { it }, animationSpec = tween(1000)
            ) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(800)),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                CardDetailsDialogContent(onDismiss = {
                    showCardDetails = false
                    showCheckoutSheet = false
                }, onCompleteOrder = {
                    showCardDetails = false
                    showCheckoutSheet = false
                    onNavigateToSuccess()
                })
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
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = PrimaryColor
            )
            Text(
                text = item.quantity,
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Normal
            )
        }

        Text(
            text = "₦ ${item.price}",
            fontFamily = BrandonGrotesque,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = PrimaryColor
        )
    }
}

@Composable
fun CheckoutDialogContent(
    onDismiss: () -> Unit, onPayOnDelivery: () -> Unit, onPayWithCard: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                AnimatedVisibility(
                    visible = showContent, enter = slideInVertically(
                        initialOffsetY = { it }, animationSpec = tween(1500)
                    ) + fadeIn()
                ) {
                    Column {
                        Text(
                            text = "Delivery address",
                            fontSize = 14.sp,
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = {
                                Text(
                                    "10th avenue, Lekki, Lagos State",
                                    color = Color.LightGray,
                                    fontFamily = BrandonGrotesque,
                                    fontWeight = FontWeight.Normal,
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF3F3F3),
                                unfocusedContainerColor = Color(0xFFF3F3F3),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedVisibility(
                    visible = showContent, enter = slideInVertically(
                        initialOffsetY = { it }, animationSpec = tween(800, delayMillis = 200)
                    ) + fadeIn()
                ) {
                    Column {
                        Text(
                            text = "Number we can call",
                            fontSize = 14.sp,
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryColor
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            placeholder = {
                                Text(
                                    "09090605708",
                                    color = Color.LightGray,
                                    fontFamily = BrandonGrotesque,
                                    fontWeight = FontWeight.Normal
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFF3F3F3),
                                unfocusedContainerColor = Color(0xFFF3F3F3),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedVisibility(
                        visible = showContent,
                        modifier = Modifier.weight(1f),
                        enter = slideInHorizontally(
                            initialOffsetX = { -it }, animationSpec = tween(800, delayMillis = 400)
                        ) + fadeIn()
                    ) {
                        OutlinedButton(
                            onClick = onPayOnDelivery,
                            modifier = Modifier.height(56.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, SecondaryColor)
                        ) {
                            Text(
                                "Pay on delivery",
                                color = SecondaryColor,
                                fontSize = 16.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    AnimatedVisibility(
                        visible = showContent,
                        modifier = Modifier.weight(1f),
                        enter = slideInHorizontally(
                            initialOffsetX = { it }, animationSpec = tween(800, delayMillis = 400)
                        ) + fadeIn()
                    ) {
                        OutlinedButton(
                            onClick = onPayWithCard,
                            modifier = Modifier.height(56.dp),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, SecondaryColor)
                        ) {
                            Text(
                                "Pay with card",
                                color = SecondaryColor,
                                fontSize = 16.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            }
        }

        // زر الإغلاق مع أنميشن ظهور بسيط
        AnimatedVisibility(
            visible = showContent,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-70).dp),
            enter = fadeIn(animationSpec = tween(1000, delayMillis = 600))
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close", tint = PrimaryColor)
            }
        }
    }
}

@Composable
fun CardDetailsDialogContent(
    onDismiss: () -> Unit, onCompleteOrder: () -> Unit
) {
    // حالة للتحكم في بدء الأنميشن عند ظهور المكون
    var showItems by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showItems = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.BottomCenter // لجعل المحتوى يرتكز في الأسفل
    ) {
        // --- 1. حاوية المحتوى (الخلفية البيضاء والحقول) ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // حاوية الحقول مع Padding جانبي
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                ) {
                    // 1. أنميشن الاسم (يظهر أولاً)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { 50 }, animationSpec = tween(600)
                        ) + fadeIn(tween(600))
                    ) {
                        Column {
                            Text(
                                text = "Card Holders Name",
                                fontSize = 15.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CardTextField(placeholder = "Adolphus Chris")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. أنميشن رقم البطاقة (يظهر بتأخير 200ms)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { 50 }, animationSpec = tween(600, delayMillis = 200)
                        ) + fadeIn(tween(600, delayMillis = 200))
                    ) {
                        Column {
                            Text(
                                text = "Card Number",
                                fontSize = 15.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CardTextField(placeholder = "1234 5678 9012 1314")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. أنميشن التاريخ والـ CCV (يظهر بتأخير 400ms)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { 50 }, animationSpec = tween(600, delayMillis = 400)
                        ) + fadeIn(tween(600, delayMillis = 400))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Date",
                                    fontSize = 15.sp,
                                    fontFamily = BrandonGrotesque,
                                    fontWeight = FontWeight.Medium,
                                    color = PrimaryColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                CardTextField(placeholder = "10/30")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "CCV",
                                    fontSize = 15.sp,
                                    fontFamily = BrandonGrotesque,
                                    fontWeight = FontWeight.Medium,
                                    color = PrimaryColor
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                CardTextField(placeholder = "123")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(45.dp))

                // 4. أنميشن الزر السفلي (يظهر بتأخير 600ms)
                AnimatedVisibility(
                    visible = showItems, enter = slideInVertically(
                        initialOffsetY = { 100 }, animationSpec = tween(600, delayMillis = 600)
                    ) + fadeIn(tween(600, delayMillis = 600))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .background(
                                color = SecondaryColor,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            )
                            .clickable { onCompleteOrder() }, contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(horizontal = 24.dp)
                        ) {
                            Text(
                                text = "Complete Order",
                                color = SecondaryColor,
                                fontSize = 16.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 30.dp, vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }

        // --- 2. زر الإغلاق (X) طافٍ فوق الـ Surface ---
        // وضعناه هنا ليكون تابعاً للـ Box الأساسي ومحاذاته TopCenter مستقلة
        AnimatedVisibility(
            visible = showItems,
            modifier = Modifier
                .align(Alignment.TopCenter) // المحاذاة بالنسبة للـ Box وليس الـ Column
                .offset(y = (-70).dp),      // رفعه للأعلى ليظهر فوق حافة الديلاوج
            enter = fadeIn(tween(800, delayMillis = 800))
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = PrimaryColor
                )
            }
        }
    }
}

@Composable
fun CardTextField(placeholder: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                placeholder, color = Color.LightGray,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Normal,
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF3F3F3),
            unfocusedContainerColor = Color(0xFFF3F3F3),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}