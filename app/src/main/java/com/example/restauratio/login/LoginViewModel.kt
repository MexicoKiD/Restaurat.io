package com.example.restauratio.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun login(email: String,
              password: String,
              onSuccess: () -> Unit,
              onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    authService.login(
                        LoginRequest(
                            email,
                            password
                        )
                    ).execute()
                }

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        sessionManager.saveAuthToken(token)
                        onSuccess.invoke()
                    } else {
                        onError.invoke("Token not received")
                    }
                } else {
                    onError.invoke("Login failed")
                }
            } catch (e: Exception) {
                onError.invoke("An error occurred")
            }
        }
    }
}