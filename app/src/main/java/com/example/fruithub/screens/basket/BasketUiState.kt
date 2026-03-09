package com.example.fruithub.screens.basket

import androidx.compose.ui.graphics.Color

data class BasketItem(
    val name: String,
    val imageRes: Int,
    val bgColor: Color,
    val price: String,
    val quantity: String
)

data class BasketUiState(
    val items: List<BasketItem> = emptyList(),
    val totalPrice: String = "₦ 60,000",
    val showCheckoutSheet: Boolean = false,
    val showCardDetails: Boolean = false
)