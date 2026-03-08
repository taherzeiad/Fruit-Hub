package com.example.fruithub.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        startAnimations()
    }

    private fun startAnimations() {
        viewModelScope.launch {
            delay(200)
            _uiState.update { it.copy(isBasketVisible = true) }
            delay(350)
            _uiState.update { it.copy(isTextVisible = true) }
            delay(200)
            _uiState.update { it.copy(isButtonVisible = true) }
        }
    }

    fun updateUserName(newName: String) {
        _uiState.update { it.copy(userName = newName) }
    }

    fun isInputValid(): Boolean = _uiState.value.userName.isNotBlank()
}