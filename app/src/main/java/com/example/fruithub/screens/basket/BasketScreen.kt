package com.example.fruithub.screens.basket

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.commonComponent.OrangeHeader
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor
import androidx.compose.foundation.shape.RoundedCornerShape
import kotlinx.coroutines.delay

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun BasketScreen(
    onBackClick: () -> Unit,
    onNavigateToSuccess: () -> Unit,
    viewModel: BasketViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val animatableProgress = remember { Animatable(0f) }
    val animationProgress by viewModel.animationProgress
    val state by viewModel.uiState

    LaunchedEffect(Unit) {
        delay(100)
        animatableProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        ) {
            viewModel.updateProgress(this.value)
        }
    }

    val headerHeight = if (animationProgress < 0.1f) screenHeight
    else 110.dp + (screenHeight - 110.dp) * (1 - animationProgress.coerceIn(0f, 1f))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
        ) {

            // قائمة العناصر
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                if (animationProgress >= 0.95f) {
                    state.items.forEachIndexed { index, item ->
                        val isLeftToRight = index % 2 == 0
                        val itemDelay = (index * 150)
                        key(index) {
                            Column {
                                BasketRow(
                                    item = item,
                                    isLeftToRight = isLeftToRight,
                                    itemDelay = itemDelay
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    color = Color(0xFFF3F3F3)
                                )
                            }
                        }
                    }
                }
            }

            if (animationProgress >= 0.98f) {
                TotalSection(
                    total = state.totalPrice,
                    onCheckoutClick = { viewModel.toggleCheckoutSheet(true) })
            }
        }

        OrangeHeader(
            title = "My Basket",
            headerHeight = headerHeight,
            onBackClick = onBackClick,
            animationProgress = animationProgress
        )

        HandleCheckoutSheets(
            showCheckoutSheet = state.showCheckoutSheet,
            showCardDetails = state.showCardDetails,
            onDismissCheckout = { viewModel.toggleCheckoutSheet(false) },
            onShowCardDetails = { viewModel.toggleCardDetails(true) },
            onDismissCardDetails = { viewModel.toggleCardDetails(false) },
            onPayOnDelivery = {
                viewModel.toggleCheckoutSheet(false)
                onNavigateToSuccess()
            },
            onCompleteOrder = {
                viewModel.toggleCheckoutSheet(false)
                onNavigateToSuccess()
            })
    }
}

@Composable
fun TotalSection(total: String, onCheckoutClick: () -> Unit) {
    val totalSlideOffset by animateDpAsState(
        targetValue = 0.dp,
        animationSpec = tween(1200, delayMillis = 800, easing = FastOutSlowInEasing),
        label = ""
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .offset(y = totalSlideOffset),
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
                total,
                fontSize = 24.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium,
                color = PrimaryColor
            )
        }

        Button(
            onClick = onCheckoutClick,
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
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BasketRow(
    item: BasketItem, isLeftToRight: Boolean, itemDelay: Int
) {
    // حالة لبدء أنميشن الصف داخلياً بعد التوقيت المحدد
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(itemDelay.toLong())
        startAnimation = true
    }

    // أنميشن الإزاحة (السلايد)
    val slideOffset by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else if (isLeftToRight) (-150).dp else 150.dp,
        animationSpec = tween(durationMillis = 800, easing = EaseOutQuart),
        label = "slide"
    )

    // أنميشن الشفافية
    val alpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "alpha"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = slideOffset)
            .graphicsLayer(alpha = alpha),
        verticalAlignment = Alignment.CenterVertically
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
fun HandleCheckoutSheets(
    showCheckoutSheet: Boolean,
    showCardDetails: Boolean,
    onDismissCheckout: () -> Unit,
    onShowCardDetails: () -> Unit,
    onDismissCardDetails: () -> Unit,
    onPayOnDelivery: () -> Unit,
    onCompleteOrder: () -> Unit
) {
    AnimatedVisibility(
        visible = showCheckoutSheet,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable {
                    if (showCardDetails) {
                        onDismissCardDetails()
                    } else {
                        onDismissCheckout()
                    }
                })
    }

    // Checkout sheet
    AnimatedVisibility(
        visible = showCheckoutSheet && !showCardDetails, enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(800, easing = FastOutSlowInEasing)
        ) + fadeIn(), exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(500)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            CheckoutDialogContent(
                onDismiss = onDismissCheckout,
                onPayOnDelivery = onPayOnDelivery,
                onPayWithCard = onShowCardDetails
            )
        }
    }

    // Card details sheet
    AnimatedVisibility(
        visible = showCheckoutSheet && showCardDetails, enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(800, easing = FastOutSlowInEasing)
        ) + fadeIn(), exit = slideOutVertically(
            targetOffsetY = { it }, animationSpec = tween(500)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            CardDetailsDialogContent(
                onDismiss = onDismissCardDetails, onCompleteOrder = onCompleteOrder
            )
        }
    }
}

@Composable
fun CheckoutDialogContent(
    onDismiss: () -> Unit, onPayOnDelivery: () -> Unit, onPayWithCard: () -> Unit
) {
    var showFields by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showFields = true
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
                    visible = showFields, enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600, easing = FastOutSlowInEasing)
                    ) + fadeIn()
                ) {
                    Column {
                        Text(
                            text = "Delivery address",
                            fontSize = 20.sp,
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
                                    fontSize = 20.sp,
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
                    visible = showFields, enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600, delayMillis = 200, easing = FastOutSlowInEasing)
                    ) + fadeIn()
                ) {
                    Column {
                        Text(
                            text = "Number we can call",
                            fontSize = 20.sp,
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
                                    fontSize = 20.sp,
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
                        visible = showFields,
                        modifier = Modifier.weight(1f),
                        enter = slideInHorizontally(
                            initialOffsetX = { -it }, animationSpec = tween(
                                700, delayMillis = 400, easing = FastOutSlowInEasing
                            )
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
                        visible = showFields,
                        modifier = Modifier.weight(1f),
                        enter = slideInHorizontally(
                            initialOffsetX = { it }, animationSpec = tween(
                                700, delayMillis = 400, easing = FastOutSlowInEasing
                            )
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

        AnimatedVisibility(
            visible = showFields,
            enter = fadeIn(animationSpec = tween(500, delayMillis = 600)) + scaleIn(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-70).dp)
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
    var showItems by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        showItems = true
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 24.dp, end = 24.dp)
                ) {
                    // 1. اسم صاحب البطاقة + الحقل (المجموعة الأولى)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(600, easing = FastOutSlowInEasing)
                        ) + fadeIn()
                    ) {
                        Column {
                            Text(
                                text = "Card Holders Name",
                                fontSize = 20.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CardTextField(placeholder = "Adolphus Chris")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. رقم البطاقة + الحقل (المجموعة الثانية - تظهر بعد 200ms)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { it }, animationSpec = tween(
                                600, delayMillis = 200, easing = FastOutSlowInEasing
                            )
                        ) + fadeIn()
                    ) {
                        Column {
                            Text(
                                text = "Card Number",
                                fontSize = 20.sp,
                                fontFamily = BrandonGrotesque,
                                fontWeight = FontWeight.Medium,
                                color = PrimaryColor
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            CardTextField(placeholder = "1234 5678 9012 1314")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. التاريخ و CCV (المجموعة الثالثة - تظهر بعد 400ms)
                    AnimatedVisibility(
                        visible = showItems, enter = slideInVertically(
                            initialOffsetY = { it }, animationSpec = tween(
                                600, delayMillis = 400, easing = FastOutSlowInEasing
                            )
                        ) + fadeIn()
                    ) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Date",
                                    fontSize = 20.sp,
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
                                    fontSize = 20.sp,
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

                // 4. زر إتمام الطلب (يظهر من الأسفل بعد 600ms)
                AnimatedVisibility(
                    visible = showItems, enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(600, delayMillis = 600, easing = FastOutSlowInEasing)
                    ) + fadeIn()
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

        // 5. زر الإغلاق
        AnimatedVisibility(
            visible = showItems,
            enter = fadeIn(animationSpec = tween(500, delayMillis = 800)) + scaleIn(),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-70).dp)
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
                placeholder,
                color = Color.LightGray,
                fontSize = 20.sp,
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