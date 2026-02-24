package com.example.fruithub.screens.basket

import androidx.compose.ui.graphics.Color

data class BasketItem(
    val name: String,
    val imageRes: Int,
    val bgColor: Color,
    val price: String,
    val quantity: String = "1 pack"
)
