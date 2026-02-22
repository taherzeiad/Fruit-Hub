package com.example.fruithub.screens.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.example.fruithub.R

@Composable
fun BasketScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- 1. الجزء العلوي البرتقالي (Header) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFA451)) // لون برتقالي أساسي
                .padding(top = 40.dp, bottom = 30.dp, start = 24.dp, end = 24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // زر Go back
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .clickable { onBackClick() }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.backicon), // أيقونة سهم بسيطة
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Go back", fontSize = 12.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.weight(0.5f))

                Text(
                    text = "My Basket",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }

        // --- 2. قائمة العناصر (بيانات وهمية مباشرة) ---
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // العنصر الأول
            BasketRow(
                name = "Quinoa fruit salad",
                quantity = "2packs",
                price = "20,000",
                imgRes = R.drawable.quinoa_salad, // تأكد من وجود هذه الأسماء في drawable
                bgColor = Color(0xFFFFFAEB)
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF3F3F3))

            // العنصر الثاني
            BasketRow(
                name = "Melon fruit salad",
                quantity = "2packs",
                price = "20,000",
                imgRes = R.drawable.melon_salad,
                bgColor = Color(0xFFF3F4F9)
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color(0xFFF3F3F3))

            // العنصر الثالث
            BasketRow(
                name = "Tropical fruit salad",
                quantity = "2packs",
                price = "20,000",
                imgRes = R.drawable.tropical_salad,
                bgColor = Color(0xFFFFF2F2)
            )
        }

        // --- 3. الجزء السفلي (السعر والزر) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = "Total", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                Text(
                    text = "₦ 60,000",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF27214D)
                )
            }

            Button(
                onClick = { /* logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA451)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(180.dp)
                    .height(56.dp)
            ) {
                Text("Checkout", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun BasketRow(name: String, quantity: String, price: String, imgRes: Int, bgColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // صورة المنتج بخلفية ملونة
        Box(
            modifier = Modifier
                .size(65.dp)
                .background(bgColor, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imgRes),
                contentDescription = null,
                modifier = Modifier.size(45.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // النصوص
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF27214D)
            )
            Text(text = quantity, fontSize = 14.sp, color = Color.Gray)
        }

        // السعر
        Text(
            text = "₦ $price",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF27214D)
        )
    }
}