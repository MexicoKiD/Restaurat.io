package com.example.restauratio.delivery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.delivery.summary.CreateOrderRequest
import com.example.restauratio.delivery.summary.Payment
import com.example.restauratio.delivery.summary.PaymentResponse
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

    private val _paymentLink = MutableLiveData<String>()
    val paymentLink: LiveData<String> get() = _paymentLink

    private val _orderId = MutableLiveData<String>()
    val orderId: LiveData<String> get() = _orderId

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
                        val orderId = orderResponse?.orderId
                        _orderId.postValue(orderId!!)

                        createPayment(orderId)

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


    private val _paymentResponse = MutableLiveData<PaymentResponse>()
    val paymentResponse: LiveData<PaymentResponse> get() = _paymentResponse

    private fun createPayment(orderId: String) {
        viewModelScope.launch {
            try {
                val paymentResponse = authService.createPayment(Payment(orderId))

                when {
                    paymentResponse.isSuccessful -> {
                        val response = paymentResponse.body()
                        _paymentResponse.postValue(response!!)
                    }
                    else -> {
                        Log.e("DeliveryViewModel", "Payment creation failed: ${paymentResponse.errorBody()}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}