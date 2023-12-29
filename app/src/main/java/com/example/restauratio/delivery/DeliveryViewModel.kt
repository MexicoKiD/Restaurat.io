package com.example.restauratio.delivery

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.delivery.summary.CreateOrderRequest
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.profile.aboutme.UserDataModel
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel  @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _userData = MutableLiveData<UserDataModel>()
    val userData: LiveData<UserDataModel> get() = _userData

    fun fetchUserData(userId: Int) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"
        viewModelScope.launch {
            val user = authService.getUserData(authToken, userId)
            _userData.postValue(user)
        }
    }

    fun updateUserData(updatedUserData: UserDataModel) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"

        viewModelScope.launch {
            try {
                val updatedUser = authService.updateUserData(authToken, updatedUserData)
                _userData.postValue(updatedUser)
            } catch (e: Exception) {
                Log.e("UpdateUserData", "Error updating user data", e)
            }
        }
    }

    fun createOrder(request: CreateOrderRequest) {
        viewModelScope.launch {
            try {
                val response = authService.createOrder(request)

                when {
                    response.isSuccessful -> {
                        val orderResponse = response.body()
                        Log.d("DeliveryViewModel", "$orderResponse")
                    }
                    else -> {
                        Log.d("DeliveryViewModel", "$response")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}