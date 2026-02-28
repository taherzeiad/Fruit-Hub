package com.example.fruithub.screens.trackorder

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.OrangeHeader // استيراد الدالة العامة
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.ui.theme.PrimaryColor

@Composable
fun DeliveryStatusScreen(onBackClick: () -> Unit) {

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    var startAnimations by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startAnimations = true
    }

    val headerHeight by animateDpAsState(
        targetValue = if (startAnimations) 110.dp else screenHeight, animationSpec = tween(
            durationMillis = 1500, easing = FastOutSlowInEasing
        ), label = "HeaderHeight"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        OrangeHeader(
            title = "Delivery Status", headerHeight = headerHeight, onBackClick = onBackClick
        )

        // BODY
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            TimelineItem(
                title = "Order Taken",
                iconRes = R.drawable.takenorder,
                iconBg = Color(0xFFFFFAEB),
                isCompleted = true
            )

            TimelineDots()

            TimelineItem(
                title = "Order Is Being Prepared",
                iconRes = R.drawable.removebgpreview,
                iconBg = Color(0xFFF3F4F9),
                isCompleted = true
            )

            TimelineDots()

            // DELIVERY STEP
            TimelineItem(
                title = "Order Is Being Delivered",
                subtitle = "Your delivery agent is coming",
                iconRes = R.drawable.deliveryman,
                iconBg = Color(0xFFFFF2F2),
                isCompleted = false,
                trailingContent = {
                    IconButton(
                        onClick = { }, modifier = Modifier
                            .background(
                                SecondaryColor.copy(alpha = 0.2f), CircleShape
                            )
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.Phone, null, tint = SecondaryColor)
                    }
                })

            TimelineDots()

            // MAP
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.rectangle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 24.dp)
                        .width(327.dp)
                        .height(128.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            TimelineItem(
                title = "Order Received",
                iconRes = Icons.Default.Check,
                iconBg = Color(0xFFE0FFE5),
                isCompleted = false,
                isFinal = true,
                trailingContent = {
                    Row {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(6.dp)
                                    .background(SecondaryColor.copy(alpha = 0.4f), CircleShape)
                            )
                        }
                    }
                })
        }
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
    trailingContent: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- المربع الأيسر ---
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(iconBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (iconRes) {
                // 1. الأيقونات العلوية (الصور): تبقى كما هي 40dp
                is Int -> Image(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                // 2. أيقونة الصح (أسفل اليسار): تظهر بالخلفية الخضراء الدائرية كما في الصورة
                is ImageVector -> {
                    Box(
                        modifier = Modifier
                            .size(45.dp) // حجم الدائرة الخضراء
                            .background(Color(0xFF4CD964), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = iconRes,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp) // حجم الصح أبيض صغير
                        )
                    }
                }
            }
        }

        Spacer(Modifier.width(20.dp))

        // --- منطقة النصوص وأيقونة الحالة اليمنى ---
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(title, fontWeight = FontWeight.Bold, color = PrimaryColor)
                    subtitle?.let {
                        Text(it, fontSize = 13.sp, color = PrimaryColor.copy(.7f))
                    }
                }

                // أيقونات الحالة على جهة اليمين (تبقى كما هي)
                when {
                    trailingContent != null -> trailingContent()
                    isCompleted -> {
                        // دائرة خضراء صغيرة مع صح للخطوات المكتملة فوق
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

    Column(
        modifier = Modifier.padding(start = 30.dp, top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .size(5.dp)
                    .background(SecondaryColor, CircleShape)
            )
        }
    }
}