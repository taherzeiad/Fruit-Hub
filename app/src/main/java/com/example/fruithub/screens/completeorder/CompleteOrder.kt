package com.example.fruithub.screens.completeorder

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor
import kotlinx.coroutines.delay

@Composable
fun OrderSuccessScreen(
    onTrackOrderClick: () -> Unit, onContinueShoppingClick: () -> Unit
) {
    // حالات التحكم في الأنميشن
    var startIconAnimation by remember { mutableStateOf(false) }
    var showTexts by remember { mutableStateOf(false) }
    var showTrackButton by remember { mutableStateOf(false) }
    var showContinueButton by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        startIconAnimation = true
        delay(600)
        showTexts = true
        delay(500)
        showTrackButton = true
        delay(400)
        showContinueButton = true
    }

    // أنميشن الأيقونة (حجم وشفافية)
    val iconScale by animateFloatAsState(
        targetValue = if (startIconAnimation) 1f else 0.3f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ), label = "IconScale"
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (startIconAnimation) 1f else 0f,
        animationSpec = tween(800),
        label = "IconAlpha"
    )

    // أنميشن النصوص (شفافية وإزاحة)
    val textAlpha by animateFloatAsState(if (showTexts) 1f else 0f, tween(600))
    val textTranslationY by animateFloatAsState(if (showTexts) 0f else 40f, tween(600))

    // أنميشن زر التتبع (شفافية وإزاحة)
    val trackAlpha by animateFloatAsState(if (showTrackButton) 1f else 0f, tween(500))
    val trackTranslationY by animateFloatAsState(if (showTrackButton) 0f else 40f, tween(500))

    // أنميشن زر المواصلة (شفافية وإزاحة)
    val continueAlpha by animateFloatAsState(if (showContinueButton) 1f else 0f, tween(500))
    val continueTranslationY by animateFloatAsState(if (showContinueButton) 0f else 40f, tween(500))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. الأيقونة ---
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(164.dp)
                .scale(iconScale)
                .alpha(iconAlpha)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0FFE5), CircleShape)
                    .border(width = 2.dp, color = Color(0xFF4CD964), shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFF4CD964), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Success",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(56.dp))

        // --- 2. النصوص (محجوزة المساحة) ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.graphicsLayer {
                alpha = textAlpha
                translationY = textTranslationY
            }) {
            Text(
                text = "Congratulations!!!",
                fontSize = 25.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium,
                color = PrimaryColor,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your order have been taken and\nis being attended to",
                fontSize = 16.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Normal,
                color = PrimaryColor,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )
        }

        Spacer(modifier = Modifier.height(56.dp))

        // --- 3. الأزرار (محجوزة المساحة) ---

        // زر تتبع الطلب
        Button(
            onClick = onTrackOrderClick,
            modifier = Modifier
                .width(133.dp)
                .height(56.dp)
                .graphicsLayer {
                    alpha = trackAlpha
                    translationY = trackTranslationY
                },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
        ) {
            Text(
                text = "Track order",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // زر مواصلة التسوق
        OutlinedButton(
            onClick = onContinueShoppingClick,
            modifier = Modifier
                .width(181.dp)
                .height(56.dp)
                .graphicsLayer {
                    alpha = continueAlpha
                    translationY = continueTranslationY
                },
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, SecondaryColor)
        ) {
            Text(
                text = "Continue shopping",
                color = SecondaryColor,
                fontSize = 14.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

