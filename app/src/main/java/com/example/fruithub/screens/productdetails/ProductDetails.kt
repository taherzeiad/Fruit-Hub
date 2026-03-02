package com.example.fruithub.screens.productdetails

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.BackButton
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor

@Composable
fun ProductDetailsScreen(onBackClick: () -> Unit, onAddToBasketClick: () -> Unit) {

    var quantity by remember { mutableStateOf(1) }

    val startAnimation = remember { mutableStateOf(false) }

    val imageScale by animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0.3f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
    )

    val sheetOffsetY by animateDpAsState(
        targetValue = if (startAnimation.value) 0.dp else 400.dp, animationSpec = tween(
            durationMillis = 700, easing = FastOutSlowInEasing
        )
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 400)
    )

    val buttonOffsetX by animateDpAsState(
        targetValue = if (startAnimation.value) 0.dp else 150.dp,
        animationSpec = tween(durationMillis = 800, delayMillis = 600)
    )
    val heartOffsetX by animateDpAsState(
        targetValue = if (startAnimation.value) 0.dp else (-100).dp,
        animationSpec = tween(durationMillis = 800, delayMillis = 600)
    )

    LaunchedEffect(Unit) {
        startAnimation.value = true
    }

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

            Image(
                painter = painterResource(id = R.drawable.quinoa_salad_large),
                contentDescription = null,
                modifier = Modifier
                    .size(176.dp)
                    .graphicsLayer(scaleX = imageScale, scaleY = imageScale)
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .offset(y = sheetOffsetY),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp), color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .graphicsLayer(alpha = contentAlpha)
            ) {
                Text(
                    text = "Quinoa Fruit Salad",
                    fontSize = 25.sp,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Normal,
                            color = PrimaryColor
                        )
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
                        fontFamily = BrandonGrotesque,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryColor
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 32.dp), color = Color(0xFFF3F3F3))

                Text(
                    text = "One Pack Contains:",
                    fontSize = 14.sp,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor
                )
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
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium,
                    color = PrimaryColor,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "If you are looking for a new fruit salad to eat today, \nquinoa is the perfect brunch for you. make ",
                    fontSize = 11.sp,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .offset(x = heartOffsetX)
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

                    Button(
                        onClick = { onAddToBasketClick() },
                        modifier = Modifier
                            .offset(x = buttonOffsetX)
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SecondaryColor),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Add to basket",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = BrandonGrotesque,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }
    }
}