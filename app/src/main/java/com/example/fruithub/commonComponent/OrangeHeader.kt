package com.example.fruithub.commonComponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    title: String, headerHeight: Dp, onBackClick: () -> Unit, animationProgress: Float
) {
    Box(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .background(SecondaryColor)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .height(80.dp)
        ) {
            BackButton(onBackClick = onBackClick)

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
        }
    }
}