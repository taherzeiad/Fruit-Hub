package com.example.fruithub.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthenticationViewModel : ViewModel() {
    var userName by mutableStateOf("")
        private set

    fun updateUserName(newName: String) {
        userName = newName
    }

    fun isInputValid(): Boolean = userName.isNotBlank()
}