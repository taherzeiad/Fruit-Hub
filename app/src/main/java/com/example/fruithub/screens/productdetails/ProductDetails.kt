package com.example.fruithub.screens.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.BackButton
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun ProductDetailsScreen(onBackClick: () -> Unit) {
    var quantity by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                BackButton(
                    onBackClick = { onBackClick() },
                    modifier = Modifier.padding(top = 40.dp, start = 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // صورة المنتج الكبيرة
            Image(
                painter = painterResource(id = R.drawable.quinoa_salad_large),
                contentDescription = null,
                modifier = Modifier.size(176.dp)
            )
        }

        // --- 2. الجزء السفلي (الخلفية البيضاء مع التفاصيل) ---
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // اسم المنتج
                Text(
                    text = "Quinoa Fruit Salad",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                // اختيار الكمية والسعر
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // زر ناقص
                        IconButton(
                            onClick = { if (quantity > 1) quantity-- },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                painterResource(R.drawable.remove),
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }

                        Text(
                            text = "$quantity",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 20.sp,
                            color = PrimaryColor
                        )

                        // زر زائد
                        IconButton(
                            onClick = { quantity++ },
                            modifier = Modifier
                                .background(Color(0xFFFFF2E7), CircleShape)
                                .size(32.dp)
                        ) {
                            Icon(
                                Icons.Default.Add, contentDescription = null, tint = SecondaryColor
                            )
                        }
                    }

                    Text(
                        text = "₦ 2,000",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 32.dp), color = Color(0xFFF3F3F3))

                // المكونات
                Text(
                    text = "One Pack Contains:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryColor
                )

                // خط صغير تحت العنوان كما في التصميم
                Box(
                    modifier = Modifier
                        .width(130.dp)
                        .height(2.dp)
                        .background(SecondaryColor)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Red Quinoa, Lime, Honey, Blueberries, Strawberries, Mango, Fresh mint.",
                    fontSize = 13.sp,
                    color = PrimaryColor,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "If you are looking for a new fruit salad to eat today, \nquinoa is the perfect brunch for you. make ",
                    fontSize = 11.sp,
                    color = Color.Black,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                // زر الإضافة للسلة والمفضلة
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // أيقونة القلب
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(Color(0xFFFFF2E7), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = SecondaryColor
                        )
                    }

                    Spacer(modifier = Modifier.width(24.dp))

                    // زر Add to basket
                    Button(
                        onClick = { /* Logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Add to basket", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}