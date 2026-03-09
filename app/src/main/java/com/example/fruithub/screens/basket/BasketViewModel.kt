package com.example.fruithub.screens.basket

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.fruithub.R

class BasketViewModel : ViewModel() {

    var animationProgress = mutableStateOf(0f)
        private set

    var uiState = mutableStateOf(BasketUiState(
        items = listOf(
            BasketItem("Quinoa fruit salad", R.drawable.quinoa_salad, Color(0xFFFFFAEB), "20,000", "2 packs"),
            BasketItem("Melon fruit salad", R.drawable.melon_salad, Color(0xFFF3F4F9), "20,000", "2 packs"),
            BasketItem("Tropical fruit salad", R.drawable.tropical_salad, Color(0xFFFFF2F2), "20,000", "2 packs")
        )
    ))
        private set

    fun updateProgress(value: Float) {
        animationProgress.value = value
    }

    fun toggleCheckoutSheet(show: Boolean) {
        uiState.value = uiState.value.copy(showCheckoutSheet = show, showCardDetails = false)
    }

    fun toggleCardDetails(show: Boolean) {
        uiState.value = uiState.value.copy(showCardDetails = show)
    }
}