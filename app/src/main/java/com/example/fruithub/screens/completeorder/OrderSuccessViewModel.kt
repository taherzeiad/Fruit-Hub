package com.example.fruithub.screens.completeorder

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OrderSuccessViewModel : ViewModel() {

    var uiState = mutableStateOf(SuccessUiState())
        private set

    init {
        startAnimationSequence()
    }

    private fun startAnimationSequence() {
        viewModelScope.launch {
            uiState.value = uiState.value.copy(startIconAnimation = true)
            delay(600)
            uiState.value = uiState.value.copy(showTexts = true)
            delay(500)
            uiState.value = uiState.value.copy(showTrackButton = true)
            delay(400)
            uiState.value = uiState.value.copy(showContinueButton = true)
        }
    }
}