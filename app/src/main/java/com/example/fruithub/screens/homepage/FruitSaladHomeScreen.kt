package com.example.fruithub.screens.homepage

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.IntOffset
import com.example.fruithub.ui.theme.BackgroundColor
import com.example.fruithub.ui.theme.CardBackground1
import com.example.fruithub.ui.theme.CardBackground2
import com.example.fruithub.ui.theme.PrimaryColor
import com.example.fruithub.ui.theme.SecondaryColor
import com.example.fruithub.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FruitSaladHomeScreen(userName: String) {
    // Animation states
    val infiniteTransition = rememberInfiniteTransition(label = "menu_transition")
    val menuAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing), repeatMode = RepeatMode.Reverse
        ), label = "menu_alpha"
    )

    // States for sequential animations
    var startGreeting by remember { mutableStateOf(false) }
    var startSearch by remember { mutableStateOf(false) }
    var startRecommendedText by remember { mutableStateOf(false) }
    var startRecommendedCards by remember { mutableStateOf(false) }
    var startTabs by remember { mutableStateOf(false) }
    var startHottestCards by remember { mutableStateOf(false) }
    var startBasket by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500) // Delay after menu animation starts
        startGreeting = true
        delay(400)
        startSearch = true
        delay(400)
        startRecommendedText = true
        delay(400)
        startRecommendedCards = true
        delay(400)
        startTabs = true
        delay(400)
        startHottestCards = true
        delay(400)
        startBasket = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // 1. Header (Menu and Basket) with animations
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            // Menu icon with fade animation
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                contentDescription = "Menu",
                tint = PrimaryColor,
                modifier = Modifier
                    .size(24.dp)
                    .graphicsLayer(alpha = menuAlpha)
            )

            // Basket with slide up animation
            AnimatedVisibility(
                visible = startBasket, enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(600, easing = FastOutSlowInEasing)
                ) + fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentSize()
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

        Spacer(modifier = Modifier.height(24.dp))

        // 2. Greeting Text with slide up animation
        AnimatedVisibility(
            visible = startGreeting, enter = slideInVertically(
                initialOffsetY = { it * 2 },
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
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
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Search Bar with slide up animation
        AnimatedVisibility(
            visible = startSearch, enter = slideInVertically(
                initialOffsetY = { it * 2 },
                animationSpec = tween(600, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
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
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 4. Recommended Section with fade animation
        AnimatedVisibility(
            visible = startRecommendedText,
            enter = fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
            val recommendedAlpha by animateFloatAsState(
                targetValue = if (startRecommendedText) 1f else 0.3f,
                animationSpec = tween(1000, easing = FastOutSlowInEasing),
                label = "recommended_alpha"
            )

            Text(
                "Recommended Combo",
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .graphicsLayer(alpha = recommendedAlpha),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold, color = PrimaryColor
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 5. Recommended Cards with scale animation
        AnimatedVisibility(
            visible = startRecommendedCards,
            enter = fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startRecommendedCards) 1f else 0.5f,
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        label = "recommended_card1_scale"
                    )
                    RecommendedCard(
                        name = "Honey lime combo",
                        price = "2,000",
                        imageRes = R.drawable.honeylime,
                        bgColor = Color.White,
                        scale = scale
                    )
                }
                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startRecommendedCards) 1f else 0.5f,
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        label = "recommended_card2_scale"
                    )
                    RecommendedCard(
                        name = "Berry mango combo",
                        price = "8,000",
                        imageRes = R.drawable.berryfruit,
                        bgColor = Color.White,
                        scale = scale
                    )
                }
                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startRecommendedCards) 1f else 0.5f,
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        label = "recommended_card2_scale"
                    )
                    RecommendedCard(
                        name = "Berry mango combo",
                        price = "8,000",
                        imageRes = R.drawable.berryfruit,
                        bgColor = Color.White,
                        scale = scale
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // 6. Tabs with sequential fade animation
        AnimatedVisibility(
            visible = startTabs,
            enter = fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
            val hottestAlpha by animateFloatAsState(
                targetValue = if (startTabs) 1f else 0.3f,
                animationSpec = tween(1000, delayMillis = 100, easing = FastOutSlowInEasing),
                label = "hottest_alpha"
            )
            val popularAlpha by animateFloatAsState(
                targetValue = if (startTabs) 1f else 0.3f,
                animationSpec = tween(1000, delayMillis = 300, easing = FastOutSlowInEasing),
                label = "popular_alpha"
            )
            val newAlpha by animateFloatAsState(
                targetValue = if (startTabs) 1f else 0.3f,
                animationSpec = tween(1000, delayMillis = 500, easing = FastOutSlowInEasing),
                label = "new_alpha"
            )
            val topAlpha by animateFloatAsState(
                targetValue = if (startTabs) 1f else 0.3f,
                animationSpec = tween(1000, delayMillis = 700, easing = FastOutSlowInEasing),
                label = "top_alpha"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Hottest",
                        fontWeight = FontWeight.Bold,
                        color = PrimaryColor,
                        fontSize = 20.sp,
                        modifier = Modifier.graphicsLayer(alpha = hottestAlpha)
                    )
                    // Show underline only when alpha is high enough
                    if (hottestAlpha > 0.8f) {
                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .height(2.dp)
                                .background(SecondaryColor)
                        )
                    }
                }
                Text(
                    "Popular",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.graphicsLayer(alpha = popularAlpha)
                )
                Text(
                    "New combo",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.graphicsLayer(alpha = newAlpha)
                )
                Text(
                    "Top",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.graphicsLayer(alpha = topAlpha)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 7. Hottest Cards with scale animation
        AnimatedVisibility(
            visible = startHottestCards,
            enter = fadeIn(animationSpec = tween(800, easing = FastOutSlowInEasing))
        ) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startHottestCards) 1f else 0.5f,
                        animationSpec = tween(800, delayMillis = 100, easing = FastOutSlowInEasing),
                        label = "hottest_card1_scale"
                    )
                    HottestCard(
                        name = "Quinoa fruit salad",
                        price = "10,000",
                        imageRes = R.drawable.breakfast,
                        bgColor = CardBackground1,
                        scale = scale
                    )
                }
                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startHottestCards) 1f else 0.5f,
                        animationSpec = tween(800, delayMillis = 300, easing = FastOutSlowInEasing),
                        label = "hottest_card2_scale"
                    )
                    HottestCard(
                        name = "Tropical fruit salad",
                        price = "10,000",
                        imageRes = R.drawable.bestever,
                        bgColor = CardBackground2,
                        scale = scale
                    )
                }

                item {
                    val scale by animateFloatAsState(
                        targetValue = if (startHottestCards) 1f else 0.5f,
                        animationSpec = tween(800, delayMillis = 300, easing = FastOutSlowInEasing),
                        label = "hottest_card2_scale"
                    )
                    HottestCard(
                        name = "Tropical fruit salad",
                        price = "10,000",
                        imageRes = R.drawable.bestever,
                        bgColor = CardBackground2,
                        scale = scale
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
                fontWeight = FontWeight.Bold,
                color = PrimaryColor,
                fontSize = 14.sp,
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
fun HottestCard(
    name: String, price: String, imageRes: Int, bgColor: Color, scale: Float = 1f
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        modifier = Modifier
            .width(160.dp)
            .height(190.dp)
            .scale(scale)
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
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(35.dp)),
                contentScale = ContentScale.Crop
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