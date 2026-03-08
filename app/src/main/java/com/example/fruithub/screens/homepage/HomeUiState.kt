package com.example.fruithub.screens.homepage

data class HomeUiState(
    val startGreeting: Boolean = false,
    val startSearch: Boolean = false,
    val startRecommendedText: Boolean = false,
    val startRecommendedCards: Boolean = false,
    val startTabs: Boolean = false,
    val startHottestCards: Boolean = false,
    val startBasket: Boolean = false
)
