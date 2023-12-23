package com.example.restauratio.login

import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    companion object {
        // Ustalony czas ważności tokena w milisekundach
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
                        LoginModel(
                            email,
                            password
                        )
                    ).execute()
                }

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        val userId = getUserIdFromToken(token)
                        Log.d("LoginViewModel", "UserID: $userId")
                        sessionManager.saveAuthToken(token, calculateExpirationTime())
                        sessionManager.saveLoggedInUserId(userId)
                        sessionManager.saveUserEmail(email)
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
        return System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD_MS
    }

    private fun getUserIdFromToken(token: String): Int {
        val parts = token.split(".")

        return if (parts.size >= 2) {
            val payload = parts[1]

            val decodedPayload = String(Base64.decode(payload, Base64.URL_SAFE), Charsets.UTF_8)

            val jsonPayload = JSONObject(decodedPayload)

            jsonPayload.getInt("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/nameidentifier")

        } else {
            -1
        }
    }
}