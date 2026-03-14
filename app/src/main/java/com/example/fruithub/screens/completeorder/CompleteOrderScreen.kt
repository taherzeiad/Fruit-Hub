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

@Composable
fun OrderSuccessScreen(
    onTrackOrderClick: () -> Unit,
    onContinueShoppingClick: () -> Unit,
    viewModel: OrderSuccessViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.uiState

    // 1. Icon Animation States
    val iconScale by animateFloatAsState(
        targetValue = if (state.startIconAnimation) 1f else 0.3f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = "IconScale"
    )
    val iconAlpha by animateFloatAsState(
        targetValue = if (state.startIconAnimation) 1f else 0f,
        animationSpec = tween(800), label = "IconAlpha"
    )

    // 2. Texts Animation States
    val textAlpha by animateFloatAsState(if (state.showTexts) 1f else 0f, tween(600), label = "")
    val textTranslationY by animateFloatAsState(
        if (state.showTexts) 0f else 40f,
        tween(600),
        label = ""
    )

    // 3. Buttons Animation States
    val trackAlpha by animateFloatAsState(
        if (state.showTrackButton) 1f else 0f,
        tween(500),
        label = ""
    )
    val trackTranslationY by animateFloatAsState(
        if (state.showTrackButton) 0f else 40f,
        tween(500),
        label = ""
    )

    val continueAlpha by animateFloatAsState(
        if (state.showContinueButton) 1f else 0f,
        tween(500),
        label = ""
    )
    val continueTranslationY by animateFloatAsState(
        if (state.showContinueButton) 0f else 40f,
        tween(500),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success Icon Section
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

        // Text Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer {
                alpha = textAlpha
                translationY = textTranslationY
            }
        ) {
            Text(
                text = "Congratulations!!!",
                fontSize = 32.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium,
                color = PrimaryColor,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your order have been taken and\nis being attended to",
                fontSize = 20.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Normal,
                color = PrimaryColor,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )
        }

        Spacer(modifier = Modifier.height(56.dp))

        // Action Buttons
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
                fontSize = 16.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

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
                fontSize = 16.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
        }
    }
}