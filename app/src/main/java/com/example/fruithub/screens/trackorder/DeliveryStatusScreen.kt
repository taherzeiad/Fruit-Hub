package com.example.fruithub.screens.trackorder

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.BackButton
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.ui.theme.PrimaryColor

@Composable
fun DeliveryStatusScreen(onBackClick: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        // HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .background(SecondaryColor)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackButton(onBackClick = onBackClick)
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "Delivery Status",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // BODY
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {

            TimelineItem(
                title = "Order Taken",
                iconRes = R.drawable.takenorder,
                iconBg = Color(0xFFFFFAEB),
                isCompleted = true
            )

            TimelineDots()

            TimelineItem(
                title = "Order Is Being Prepared",
                iconRes = R.drawable.removebgpreview,
                iconBg = Color(0xFFF3F4F9),
                isCompleted = true
            )

            TimelineDots()

            // DELIVERY STEP
            TimelineItem(
                title = "Order Is Being Delivered",
                subtitle = "Your delivery agent is coming",
                iconRes = R.drawable.deliveryman,
                iconBg = Color(0xFFFFF2F2),
                isCompleted = false,
                trailingContent = {
                    IconButton(
                        onClick = { }, modifier = Modifier
                            .background(
                                SecondaryColor.copy(alpha = 0.2f), CircleShape
                            )
                            .size(40.dp)
                    ) {
                        Icon(Icons.Default.Phone, null, tint = SecondaryColor)
                    }
                })

            TimelineDots()

            // MAP
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.rectangle),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 24.dp)
                        .width(327.dp)
                        .height(128.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            TimelineItem(
                title = "Order Received",
                iconRes = Icons.Default.Check,
                iconBg = Color(0xFFE0FFE5),
                isCompleted = false,
                isFinal = true,
                trailingContent = {
                    Row {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 3.dp)
                                    .size(6.dp)
                                    .background(
                                        SecondaryColor.copy(alpha = 0.4f), CircleShape
                                    )
                            )
                        }
                    }
                })
        }
    }
}

@Composable
fun TimelineItem(
    title: String,
    iconRes: Any,
    iconBg: Color,
    isCompleted: Boolean,
    subtitle: String? = null,
    isFinal: Boolean = false,
    trailingContent: @Composable (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // ICON
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(iconBg, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (iconRes) {
                is Int -> Image(
                    painterResource(iconRes), null, modifier = Modifier.size(40.dp)
                )

                is ImageVector -> Icon(
                    iconRes, null, tint = Color(0xFF4CD964), modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(Modifier.width(20.dp))

        // TEXT AREA
        Column(modifier = Modifier.weight(1f)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text(title, fontWeight = FontWeight.Bold, color = PrimaryColor)
                    subtitle?.let {
                        Text(it, fontSize = 13.sp, color = PrimaryColor.copy(.7f))
                    }
                }

                when {
                    trailingContent != null -> trailingContent()
                    isCompleted -> Icon(
                        Icons.Default.CheckCircle, null, tint = Color(0xFF4CD964)
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineDots() {

    Column(
        modifier = Modifier.padding(start = 30.dp, top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(3) {
            Box(
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .size(5.dp)
                    .background(SecondaryColor, CircleShape)
            )
        }
    }
}