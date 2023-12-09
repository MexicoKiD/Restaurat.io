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

    companion object {
        // Ustalony czas ważności tokena w milisekundach (np. 1 godzina)
        private const val TOKEN_VALIDITY_PERIOD_MS = 60 * 60 * 1000
    }

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
                        sessionManager.saveAuthToken(token, calculateExpirationTime())
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

    private fun calculateExpirationTime(): Long {
        // Oblicz czas wygaśnięcia tokena na podstawie bieżącego czasu i ustalonego okresu ważności
        return System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD_MS
    }

}