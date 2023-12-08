package com.example.restauratio.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {

    fun register(
        email: String,
        password: String,
        passwordAgain: String,
        firstName: String,
        lastName: String,
        address: String,
        city: String,
        postalCode: String,
        country: String,
        phone: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    authService.register(
                        RegisterRequest(
                            email,
                            password,
                            passwordAgain,
                            firstName,
                            lastName,
                            address,
                            city,
                            postalCode,
                            country,
                            phone
                        )
                    )
                }

                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody.takeIf { it?.isNotEmpty() == true } ?: "Empty error body"
                    onError.invoke("Registration failed: $errorMessage")
                }
            } catch (e: Exception) {
                onError.invoke("An error occurred")
            }
        }
    }
}