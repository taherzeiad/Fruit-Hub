package com.example.fruithub.screens.trackorder

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.OrangeHeader
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.ui.theme.PrimaryColor

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun DeliveryStatusScreen(onBackClick: () -> Unit) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var startAnimations by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimations = true
    }

    val headerHeight by animateDpAsState(
        targetValue = if (startAnimations) 110.dp else screenHeight,
        animationSpec = tween(durationMillis = 1700, easing = FastOutSlowInEasing),
        label = "HeaderHeight"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            // 1. Order Taken
            AnimatedVisibility(
                visible = startAnimations && headerHeight < 500.dp, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(1000, delayMillis = 400)
                ) + fadeIn()
            ) {
                Column {
                    TimelineItem("Order Taken", R.drawable.takenorder, Color(0xFFFFFAEB), true)
                    TimelineDots()
                }
            }

            // 2. Order Is Being Prepared
            AnimatedVisibility(
                visible = startAnimations && headerHeight < 400.dp, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(1000, delayMillis = 600)
                ) + fadeIn()
            ) {
                Column {
                    TimelineItem(
                        "Order Is Being Prepared",
                        R.drawable.removebgpreview,
                        Color(0xFFF3F4F9),
                        true,
                        topPadding = 0.dp
                    )
                    TimelineDots()
                }
            }

            // 3. Order Is Being Delivered
            AnimatedVisibility(
                visible = startAnimations && headerHeight < 300.dp, enter = slideInHorizontally(
                    initialOffsetX = { -it }, animationSpec = tween(1000, delayMillis = 800)
                ) + fadeIn()
            ) {
                Column {
                    TimelineItem(
                        title = "Order Is Being Delivered",
                        subtitle = "Your delivery agent is coming",
                        iconRes = R.drawable.deliveryman,
                        iconBg = Color(0xFFFFF2F2),
                        isCompleted = false,
                        topPadding = 0.dp,
                        trailingContent = {
                            IconButton(
                                onClick = { },
                                modifier = Modifier
                                    .background(SecondaryColor, CircleShape)
                                    .size(40.dp)
                            ) {
                                Icon(painterResource(R.drawable.iconcall), null, tint = Color.White)
                            }
                        })
                    TimelineDots()
                }
            }

            // 4. Photo (Plan)
            val imageWidth by animateDpAsState(
                targetValue = if (startAnimations && headerHeight < 250.dp) 327.dp else 0.dp,
                animationSpec = tween(1500, delayMillis = 1200)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(R.drawable.rectangle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 24.dp)
                        .width(imageWidth)
                        .height(128.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // 5. Order Received
            AnimatedVisibility(
                visible = startAnimations && headerHeight < 200.dp, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(800, delayMillis = 1000)
                ) + fadeIn()
            ) {
                TimelineItem(
                    title = "Order Received",
                    iconRes = Icons.Default.Check,
                    iconBg = Color(0xFFE0FFE5),
                    isCompleted = false,
                    isFinal = true,
                    topPadding = 0.dp,
                    trailingContent = {
                        Row {
                            repeat(3) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 3.dp)
                                        .size(8.dp)
                                        .background(SecondaryColor.copy(alpha = 0.4f), CircleShape)
                                )
                            }
                        }
                    })
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

        OrangeHeader(
            title = "Delivery Status", headerHeight = headerHeight, onBackClick = onBackClick
        )
    }
}

@Composable
fun TimelineItem(
    title: String,
    iconRes: Any,
    iconBg: Color,
    isCompleted: Boolean,
    subtitle: String? = null,
    isFinal: Boolean = false,
    topPadding: Dp = 24.dp,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = topPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(iconBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (iconRes) {
                is Int -> Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                is ImageVector -> {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(Color(0xFF4CD964), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconRes,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.width(20.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        title,
                        color = PrimaryColor,
                        fontSize = 16.sp,
                        fontFamily = BrandonGrotesque,
                        fontWeight = FontWeight.Medium
                    )
                    subtitle?.let {
                        Text(
                            it,
                            fontSize = 14.sp,
                            color = PrimaryColor.copy(.7f),
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                when {
                    trailingContent != null -> trailingContent()
                    isCompleted -> {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color(0xFF4CD964), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TimelineDots() {
    Canvas(
        modifier = Modifier
            .padding(start = 31.dp)
            .height(55.dp)
            .width(2.dp)
    ) {
        // floatArrayOf(8f, 12f) تعني: طول النقطة 8، والفراغ بينهما 12
        val pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 12f), 0f)

        drawLine(
            color = SecondaryColor,
            start = androidx.compose.ui.geometry.Offset(size.width / 2, 0f),
            end = androidx.compose.ui.geometry.Offset(size.width / 2, size.height),
            pathEffect = pathEffect,
            strokeWidth = size.width,
            cap = androidx.compose.ui.graphics.StrokeCap.Square
        )
    }
}