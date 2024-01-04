package com.example.restauratio.passwordRemind

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.login.LoginModel
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PasswordRemindViewModel @Inject constructor(
    private val authService: AuthService,
) : ViewModel() {

    fun passwordRemind(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    authService.passwordRemind(PasswordRemindModel(email))
                }
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody.takeIf { it?.isNotEmpty() == true } ?: "Empty error body"
                    onError.invoke("Password remind failed: $errorMessage")
                }
            }catch (e: Exception) {
                onError.invoke("An error occurred")
            }
        }
    }
}