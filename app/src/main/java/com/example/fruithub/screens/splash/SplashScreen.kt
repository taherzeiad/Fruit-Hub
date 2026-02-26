package com.example.fruithub.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.fruithub.R
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    var startCenterBox by remember { mutableStateOf(false) }
    var startRightBox by remember { mutableStateOf(false) }
    var startLeftBox by remember { mutableStateOf(false) }
    var startLeaves by remember { mutableStateOf(false) }
    var startText by remember { mutableStateOf(false) }

    // 🟠 لبدء توسع الدائرة
    var expandCircle by remember { mutableStateOf(false) }

    // أنيميشن نصف قطر الدائرة
    val circleRadius by animateFloatAsState(
        targetValue = if (expandCircle) 2500f else 0f, // قيمة كبيرة لتغطية الشاشة بالكامل
        animationSpec = tween(durationMillis = 3000, easing = FastOutSlowInEasing),
        label = "CircleExpand"
    )

    val centerBoxOffset by animateDpAsState(
        targetValue = if (startCenterBox) 22.dp else (-400).dp,
        animationSpec = tween(600),
        label = ""
    )

    val rightBoxOffset by animateDpAsState(
        targetValue = if (startRightBox) (-55).dp else 300.dp,
        animationSpec = tween(600),
        label = ""
    )

    val leftBoxOffset by animateDpAsState(
        targetValue = if (startLeftBox) 55.dp else (-300).dp, animationSpec = tween(600), label = ""
    )

    val leavesOffset by animateDpAsState(
        targetValue = if (startLeaves) 68.dp else (-200).dp, animationSpec = tween(500), label = ""
    )

    val textOffset by animateDpAsState(
        targetValue = if (startText) -40.dp else 200.dp, animationSpec = tween(600), label = ""
    )

    // ✨ التحكم بالظهور
    val leavesAlpha by animateFloatAsState(if (startLeaves) 1f else 0f, label = "")
    val textAlpha by animateFloatAsState(if (startText) 1f else 0f, label = "")

    LaunchedEffect(Unit) {
        delay(300)
        startCenterBox = true
        delay(500)
        startRightBox = true
        delay(300)
        startLeftBox = true
        delay(300)
        startLeaves = true
        startText = true

        // ⏱️ انتظر قليلاً بعد ظهور الشعار ثم ابدأ الدائرة البرتقالية
        delay(1150)
        expandCircle = true

        // انتظر حتى يكتمل أنيميشن الدائرة قبل الانتقال
        delay(800)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // 🌱 النبتة
            Image(
                painter = painterResource(R.drawable.tree),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 47.dp)
                    .size(59.dp)
                    .offset(y = leavesOffset)
                    .alpha(leavesAlpha)   // مخفية ثم تظهر
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(R.drawable.red),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.27.dp)
                        .offset(x = leftBoxOffset, y = 75.dp)
                )

                Spacer(modifier = Modifier.width((-300).dp))

                Image(
                    painter = painterResource(R.drawable.orangeright),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .offset(y = centerBoxOffset)
                )

                Spacer(modifier = Modifier.width((-300).dp))

                Image(
                    painter = painterResource(R.drawable.orangedark),
                    contentDescription = null,
                    modifier = Modifier
                        .size(98.dp)
                        .offset(x = rightBoxOffset, y = 75.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🟧 مستطيل النص
            Image(
                painter = painterResource(R.drawable.textfruit),
                contentDescription = null,
                modifier = Modifier
                    .offset(y = textOffset)
                    .alpha(textAlpha)   // مخفي ثم يظهر
            )
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = SecondaryColor, // استخدم لون OrangePrimary الخاص بك
                radius = circleRadius, center = androidx.compose.ui.geometry.Offset(
                    x = size.width + 100f, // خارج الشاشة قليلاً جهة اليمين
                    y = size.height + 100f // خارج الشاشة قليلاً جهة الأسفل
                )
            )
        }
    }
}