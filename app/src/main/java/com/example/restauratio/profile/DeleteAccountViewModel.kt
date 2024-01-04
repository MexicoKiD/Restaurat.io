package com.example.restauratio.profile

import androidx.lifecycle.ViewModel
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    suspend fun deleteAccount(userId: Int) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"
        return try {
            val response = authService.deleteUser(authToken,sessionManager.getLoggedInUserId())
            if (response.isSuccessful) {
            } else {
            }
        } catch (_: Exception) {
        }
    }
}