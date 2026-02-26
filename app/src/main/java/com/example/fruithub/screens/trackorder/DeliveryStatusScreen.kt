package com.example.fruithub.screens.trackorder

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.BackButton
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.ui.theme.PrimaryColor

@Composable
fun DeliveryStatusScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- 1. الهيدر البرتقالي ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(SecondaryColor)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(onBackClick = onBackClick)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Delivery Status",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // --- 2. محتوى الخط الزمني (Scrollable) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // الحالة 1: Order Taken
            TimelineItem(
                title = "Order Taken", iconRes = R.drawable.takenorder, // استبدل بأيقوناتك
                iconBg = Color(0xFFFFFAEB), isCompleted = true, showLine = true
            )

            // الحالة 2: Order Is Being Prepared
            TimelineItem(
                title = "Order Is Being Prepared",
                iconRes = R.drawable.removebgpreview,
                iconBg = Color(0xFFF3F4F9),
                isCompleted = true,
                showLine = true
            )

            // الحالة 3: Order Is Being Delivered + الاتصال + الخريطة
            TimelineItem(
                title = "Order Is Being Delivered",
                subtitle = "Your delivery agent is coming",
                iconRes = R.drawable.deliveryman,
                iconBg = Color(0xFFFFF2F2),
                isCompleted = false,
                showLine = true,
                trailingContent = {
                    // أيقونة الاتصال البرتقالية
                    IconButton(
                        onClick = { /* Call logic */ }, modifier = Modifier
                            .background(
                                SecondaryColor.copy(alpha = 0.2f), CircleShape
                            )
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = null, tint = SecondaryColor)
                    }
                },
                extraContent = {
                    Image(
                        painter = painterResource(id = R.drawable.rectangle),
                        contentDescription = "Map",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(vertical = 16.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                })

            // الحالة 4: Order Received
            TimelineItem(
                title = "Order Received",
                iconRes = Icons.Default.Check,
                iconBg = Color(0xFFE0FFE5),
                isCompleted = false,
                isFinal = true,
                showLine = false,
                trailingContent = {
                    // النقاط الثلاث
                    Row {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 2.dp)
                                    .size(8.dp)
                                    .background(SecondaryColor.copy(alpha = 0.3f), CircleShape)
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
    showLine: Boolean = false,
    isFinal: Boolean = false,
    trailingContent: @Composable (() -> Unit)? = null,
    extraContent: @Composable (() -> Unit)? = null
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        // الجزء الأيسر: الأيقونة والخط المنقط
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(iconBg, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (iconRes) {
                    is Int -> {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    is ImageVector -> {
                        Icon(
                            imageVector = iconRes,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp),
                            tint = Color(0xFF4CD964) // لون علامة الصح الخضراء
                        )
                    }
                }
            }
            if (showLine) {
                Spacer(modifier = Modifier.height(9.dp))

                Canvas(
                    modifier = Modifier
                        .width(2.dp)
                        .height(70.dp)
                ) {
                    drawLine(
                        color = SecondaryColor,
                        start = Offset(size.width / 2, 0f),
                        end = Offset(size.width / 2, size.height),
                        strokeWidth = size.width,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f), 0f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        // الجزء الأوسط والأيمن: النصوص والحالات
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            fontSize = 14.sp,
                            color = PrimaryColor.copy(alpha = 0.7f)
                        )
                    }
                }

                if (trailingContent != null) {
                    trailingContent()
                } else if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4CD964),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            if (extraContent != null) {
                extraContent()
            }

            if (!isFinal) Spacer(modifier = Modifier.height(20.dp))
        }
    }
}