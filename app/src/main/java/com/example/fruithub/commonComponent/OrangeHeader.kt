package com.example.fruithub.commonComponent

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun OrangeHeader(
    title: String,
    headerHeight: Dp,
    onBackClick: () -> Unit,
    animationProgress: Float
) {
    val contentAlpha by animateDpAsState(
        targetValue = if (animationProgress > 0.65f) 1.dp else 0.dp,
        animationSpec = tween(durationMillis = 300),
        label = "ContentAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .background(SecondaryColor)
            .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (contentAlpha > 0.dp) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                BackButton(
                    onBackClick = onBackClick,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.weight(0.3f))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}