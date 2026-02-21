package com.example.fruithub.screens.homepage

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.ui.theme.BackgroundColor
import com.example.fruithub.ui.theme.CardBackground1
import com.example.fruithub.ui.theme.CardBackground2
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.R


@Composable
fun FruitSaladHomeScreen(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. Header (Menu and Basket)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp), // أضفنا مسافة عمودية بسيطة
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top // المحاذاة للأعلى لتبدو الأيقونات متناسقة
        ) {
            // أيقونة القائمة (اليسار)
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                contentDescription = "Menu",
                tint = PrimaryColor,
                modifier = Modifier.size(24.dp) // حجم مناسب لأيقونة المنيو
            )

            // العمود الذي يحتوي على السلة والنص (اليمين)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.wrapContentSize() // لضمان عدم وجود مساحة وهمية
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mybasket),
                    contentDescription = "Basket",
                    tint = SecondaryColor, // 👈 استخدم tint بدلاً من color
                    modifier = Modifier.size(24.dp) // 👈 قمنا بتصغير الحجم ليتناسب مع التصميم ويحذف الفراغ
                )
                Text(
                    text = "My basket",
                    fontSize = 10.sp,
                    color = PrimaryColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Greeting Text
        Text(
            text = "Hello $userName, What fruit salad combo do you want today?",
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                lineHeight = 32.sp
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Search Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search for fruit salad combos", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .clip(RoundedCornerShape(16.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF3F4F9),
                    focusedContainerColor = Color(0xFFF3F4F9),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "Settings",
                modifier = Modifier.size(24.dp),
                tint = Color.Black // 👈 هنا تضع اللون الذي تريده
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 4. Recommended Section
        Text(
            "Recommended Combo",
            modifier = Modifier.padding(horizontal = 24.dp),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold, color = PrimaryColor
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { RecommendedCard("Honey lime combo", "2,000", Color.White) }
            item { RecommendedCard("Berry mango combo", "8,000", Color.White) }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 5. Tabs (Hottest, Popular, etc.)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Hottest", fontWeight = FontWeight.Bold, color = PrimaryColor, fontSize = 20.sp
                )
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(2.dp)
                        .background(SecondaryColor)
                )
            }
            Text("Popular", color = Color.Gray, fontSize = 16.sp)
            Text("New combo", color = Color.Gray, fontSize = 16.sp)
            Text("Top", color = Color.Gray, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 6. Bottom Grid/List (Hottest Items)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { HottestCard("Quinoa fruit salad", "10,000", CardBackground1) }
            item { HottestCard("Tropical fruit salad", "10,000", CardBackground2) }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RecommendedCard(name: String, price: String, bgColor: Color) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false,
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // لتعبئة مساحة البطاقة المحددة
                .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // أيقونة القلب
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = SecondaryColor,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(20.dp)
            )

            // صورة المنتج
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFF3F4F9), RoundedCornerShape(40.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            // اسم المنتج - استخدمنا weight لضمان محاذاة العناصر التي تحته
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f) // 👈 يجعل النص يأخذ المساحة المتاحة ويوحد مكان السعر
            )

            // السطر السفلي (السعر والزر)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₦ $price",
                    color = SecondaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color(0xFFFFFAEB), RoundedCornerShape(12.dp))
                        .clickable { /* logic */ }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = SecondaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HottestCard(name: String, price: String, bgColor: Color) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier
            .width(160.dp)
            .height(190.dp)   // ← ثبت الارتفاع
    ) {
        Column(
            modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = SecondaryColor,
                modifier = Modifier.align(Alignment.End)
            )
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .background(Color.LightGray, RoundedCornerShape(35.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(name, fontWeight = FontWeight.Bold, color = PrimaryColor, fontSize = 12.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("₦ $price", color = SecondaryColor)
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(SecondaryColor.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = SecondaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}