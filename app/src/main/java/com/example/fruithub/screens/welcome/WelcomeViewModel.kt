package com.example.fruithub.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WelcomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WelcomeUiState())
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

    init {
        startAnimations()
    }

    private fun startAnimations() {
        viewModelScope.launch {
            delay(300)
            _uiState.update { it.copy(isBasketVisible = true) }
            delay(600)
            _uiState.update { it.copy(isTextVisible = true) }
            delay(400)
            _uiState.update { it.copy(isButtonVisible = true) }
        }
    }
}