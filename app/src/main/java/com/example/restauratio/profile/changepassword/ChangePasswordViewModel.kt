package com.example.restauratio.profile.changepassword
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

     suspend fun resetPassword(password: String, passwordAgain: String) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"
        val userEmail = sessionManager.getUserEmail()

        val resetPasswordRequest = ChangePasswordModel(
            email = userEmail,
            password = password,
            passwordAgain = passwordAgain
        )

         withContext(Dispatchers.IO) {
             val response = authService.resetPassword(authToken, resetPasswordRequest)
             if (response.isSuccessful) {
                 val resetPasswordResponse = response.body()
                 if (resetPasswordResponse != null) {
                     Log.e("ChangePasswordViewModel", "Password reset")
                 } else {
                     Log.e("ChangePasswordViewModel", "Password reset error")
                 }
             } else {
                 val errorBody = response.errorBody()?.string()
                 Log.e("ChangePasswordViewModel", "Password reset error: $errorBody")
             }
         }
    }
}