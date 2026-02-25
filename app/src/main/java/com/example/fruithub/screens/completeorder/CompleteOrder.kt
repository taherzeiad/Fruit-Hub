package com.example.fruithub.screens.completeorder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun OrderSuccessScreen(
    onTrackOrderClick: () -> Unit,
    onContinueShoppingClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(164.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE0FFE5), CircleShape)
                    .border(
                        width = 2.dp, // يمكنك تعديل سمك الإطار من هنا
                        color = Color(0xFF4CD964),
                        shape = CircleShape
                    )
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

        // --- 2. النصوص ---
        Text(
            text = "Congratulations!!!",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF27214D),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Your order have been taken and\nis being attended to",
            fontSize = 16.sp,
            color = Color(0xFF27214D),
            textAlign = TextAlign.Center,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(56.dp))

        // --- 3. الأزرار ---

        // زر تتبع الطلب (خلفية ملونة)
        Button(
            onClick = onTrackOrderClick,
            modifier = Modifier
                .width(133.dp)
                .height(56.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor)
        ) {
            Text(
                text = "Track order",
                color = Color.White,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // زر مواصلة التسوق (حدود ملونة)
        OutlinedButton(
            onClick = onContinueShoppingClick,
            modifier = Modifier
                .width(181.dp)
                .height(56.dp),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, SecondaryColor)
        ) {
            Text(
                text = "Continue shopping",
                color = SecondaryColor,
                fontSize = 14.sp
            )
        }
    }
}