package com.example.fruithub.commonComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun CheckoutDialogContent(
    onDismiss: () -> Unit,
    onPayOnDelivery: () -> Unit,
    onPayWithCard: () -> Unit
) {
    // نستخدم Box هنا لترتيب زر الإغلاق فوق جسم الديلاوج
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // جسم الديلاوج الأبيض (استخدمنا Surface بدلاً من ModalBottomSheet)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            color = Color.White,
            shadowElevation = 8.dp // لإعطاء عمق بسيط
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Text(
                    text = "Delivery address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27214D)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "10th avenue, Lekki, Lagos State",
                            color = Color.LightGray
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

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Number we can call",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27214D)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("09090605708", color = Color.LightGray) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF3F3F3),
                        unfocusedContainerColor = Color(0xFFF3F3F3),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onPayOnDelivery,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, SecondaryColor)
                    ) {
                        Text("Pay on delivery", color = SecondaryColor, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    OutlinedButton(
                        onClick = onPayWithCard,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, SecondaryColor)
                    ) {
                        Text("Pay with card", color = SecondaryColor, fontSize = 16.sp)
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        // زر الإغلاق (X) المخصص - يوضع خارج الـ Surface ليكون عائماً
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-70).dp)
                .background(Color.White, CircleShape)
                .size(48.dp)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = Color(0xFF27214D))
        }
    }
}