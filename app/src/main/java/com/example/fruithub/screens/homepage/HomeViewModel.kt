package com.example.fruithub.screens.homepage

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fruithub.R
import com.example.fruithub.ui.theme.CardBackground1
import com.example.fruithub.ui.theme.CardBackground2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FruitProduct(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int,
    val bgColor: Color = Color.White
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // البيانات الوهمية للقسم الموصى به
    val recommendedProducts = listOf(
        FruitProduct(1, "Honey lime combo", "2,000", R.drawable.honeylime, Color.White),
        FruitProduct(2, "Berry mango combo", "8,000", R.drawable.berryfruit, Color.White),
        FruitProduct(3, "Quinoa fruit salad", "10,000", R.drawable.breakfast, Color.White)
    )

    // البيانات الوهمية للقسم الأكثر طلباً باستخدام ألوان الثيم الخاصة بك
    val hottestProducts = listOf(
        FruitProduct(4, "Tropical fruit salad", "10,000", R.drawable.bestever, CardBackground1),
        FruitProduct(5, "Melon fruit mix", "5,000", R.drawable.berryfruit, CardBackground2),
        FruitProduct(6, "Special Breakfast", "12,000", R.drawable.breakfast, CardBackground1)
    )

    init {
        startSequenceAnimations()
    }

    private fun startSequenceAnimations() {
        viewModelScope.launch {
            delay(500)
            _uiState.update { it.copy(startGreeting = true) }
            delay(400)
            _uiState.update { it.copy(startSearch = true) }
            delay(400)
            _uiState.update { it.copy(startRecommendedText = true) }
            delay(400)
            _uiState.update { it.copy(startRecommendedCards = true) }
            delay(400)
            _uiState.update { it.copy(startTabs = true) }
            delay(400)
            _uiState.update { it.copy(startHottestCards = true) }
            delay(400)
            _uiState.update { it.copy(startBasket = true) }
        }
    }
}