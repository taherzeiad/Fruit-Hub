package com.example.fruithub.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruithub.R
import com.example.fruithub.commonComponent.ButtonOrange
import com.example.fruithub.ui.theme.BrandonGrotesque
import com.example.fruithub.ui.theme.SecondaryColor
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(onContinueClick: () -> Unit) {

    var isBasketVisible by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var isButtonVisible by remember { mutableStateOf(false) }

    val basketScale by animateFloatAsState(
        targetValue = if (isBasketVisible) 1f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        ), label = "BasketScale"
    )

    LaunchedEffect(Unit) {
        delay(300)
        isBasketVisible = true
        delay(600)
        isTextVisible = true
        delay(400)
        isButtonVisible = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .background(SecondaryColor)
        ) {
            Image(
                painter = painterResource(id = R.drawable.smallfruit),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 85.dp, end = 10.dp)
                    .size(45.dp)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = basketScale, scaleY = basketScale
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fruit_basket),
                    contentDescription = "Fruit Basket",
                    modifier = Modifier
                        .width(320.dp)
                        .height(280.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(4.dp))

                Image(
                    painter = painterResource(id = R.drawable.basket_shadow),
                    contentDescription = null,
                    modifier = Modifier
                        .width(280.dp)
                        .height(14.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Spacer(modifier = Modifier.height(38.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                visible = isTextVisible,
                enter = slideInVertically(initialOffsetY = { it / 2 }) + fadeIn()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Get The Freshest Fruit Salad Combo",
                        fontSize = 20.sp,
                        fontFamily = BrandonGrotesque,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF27214D),
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "We deliver the best and freshest fruit salad in town. Order for a combo today!!!",
                        fontSize = 16.sp,
                        fontFamily = BrandonGrotesque,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF5D577E),
                        lineHeight = 24.sp
                    )
                }
            }

            AnimatedVisibility(
                visible = isButtonVisible,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                ButtonOrange(
                    onClick = onContinueClick, modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "Let’s Continue",
                        color = Color.White,
                        fontFamily = BrandonGrotesque,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}