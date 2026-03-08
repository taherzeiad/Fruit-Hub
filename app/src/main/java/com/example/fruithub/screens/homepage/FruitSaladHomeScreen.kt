package com.example.fruithub.screens.homepage

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fruithub.ui.theme.BackgroundColor
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.R
import com.example.fruithub.ui.theme.BrandonGrotesque

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FruitSaladHomeScreen(
    userName: String,
    onBasketClick: () -> Unit,
    onProductClick: () -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val infiniteTransition = rememberInfiniteTransition(label = "menu_transition")
    val menuAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "menu_alpha"
    )

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
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .height(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                contentDescription = "Menu",
                tint = PrimaryColor,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer(alpha = menuAlpha)
            )

            AnimatedVisibility(
                visible = uiState.startBasket,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(tween(800)),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { onBasketClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mybasket),
                        contentDescription = "Basket",
                        tint = SecondaryColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "My basket",
                        fontSize = 10.sp,
                        color = PrimaryColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // 2. Greeting Text
        AnimatedVisibility(
            visible = uiState.startGreeting,
            enter = slideInVertically(initialOffsetY = { it * 2 }) + fadeIn()
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Medium, fontFamily = BrandonGrotesque)) {
                        append("Hello $userName, ")
                    }
                    withStyle(SpanStyle(fontWeight = FontWeight.Normal, fontFamily = BrandonGrotesque)) {
                        append("What fruit salad \ncombo do you want today?")
                    }
                },
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 20.sp, color = PrimaryColor, lineHeight = 28.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Search Bar
        AnimatedVisibility(
            visible = uiState.startSearch,
            enter = slideInVertically(initialOffsetY = { it * 2 }) + fadeIn()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text("Search for fruit salad combos", color = Color.Gray, fontFamily = BrandonGrotesque)
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.weight(1f).height(56.dp).clip(RoundedCornerShape(16.dp)),
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
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 4. Recommended Section Header
        AnimatedVisibility(visible = uiState.startRecommendedText, enter = fadeIn()) {
            Text(
                "Recommended Combo",
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 24.dp),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = BrandonGrotesque, fontWeight = FontWeight.Medium, color = PrimaryColor
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 5. Recommended Cards (Dynamic List)
        AnimatedVisibility(visible = uiState.startRecommendedCards, enter = fadeIn()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.recommendedProducts) { product ->
                    val scale by animateFloatAsState(
                        targetValue = if (uiState.startRecommendedCards) 1f else 0.5f,
                        animationSpec = tween(800), label = "scale"
                    )
                    RecommendedCard(
                        name = product.name,
                        price = product.price,
                        imageRes = product.imageRes,
                        bgColor = product.bgColor,
                        scale = scale
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 6. Tabs
        AnimatedVisibility(visible = uiState.startTabs, enter = fadeIn()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Hottest", fontWeight = FontWeight.Bold, color = PrimaryColor, fontSize = 20.sp)
                    Box(modifier = Modifier.width(20.dp).height(2.dp).background(SecondaryColor))
                }
                Text("Popular", color = Color.Gray, fontFamily = BrandonGrotesque, fontSize = 16.sp)
                Text("New combo", color = Color.Gray, fontFamily = BrandonGrotesque, fontSize = 16.sp)
                Text("Top", color = Color.Gray, fontFamily = BrandonGrotesque, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 7. Hottest Cards (Dynamic List)
        AnimatedVisibility(visible = uiState.startHottestCards, enter = fadeIn()) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.hottestProducts) { product ->
                    val scale by animateFloatAsState(
                        targetValue = if (uiState.startHottestCards) 1f else 0.5f,
                        animationSpec = tween(800), label = "scale"
                    )
                    HottestCard(
                        name = product.name,
                        price = product.price,
                        imageRes = product.imageRes,
                        bgColor = product.bgColor,
                        scale = scale,
                        onClick = onProductClick
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun RecommendedCard(
    name: String, price: String, imageRes: Int, bgColor: Color, scale: Float = 1f
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(210.dp)
            .scale(scale)
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
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = SecondaryColor,
                modifier = Modifier
                    .align(Alignment.End)
                    .size(20.dp)
            )

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(40.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = name,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium,
                color = PrimaryColor,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₦ $price",
                    color = SecondaryColor,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Normal,
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
fun HottestCard(
    name: String,
    price: String,
    imageRes: Int,
    bgColor: Color,
    scale: Float = 1f,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier
            .width(160.dp)
            .height(190.dp)
            .scale(scale)
            .clickable { onClick() }) {
        Column(
            modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = SecondaryColor,
                modifier = Modifier.align(Alignment.End)
            )
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(35.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                name,
                color = PrimaryColor,
                fontSize = 16.sp,
                fontFamily = BrandonGrotesque,
                fontWeight = FontWeight.Medium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "₦ $price",
                    color = SecondaryColor,
                    fontFamily = BrandonGrotesque,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
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