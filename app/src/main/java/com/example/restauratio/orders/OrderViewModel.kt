package com.example.restauratio.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restauratio.loginSession.SessionManager
import com.example.restauratio.request.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val apiService: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> get() = _orders

    fun getOrders(statusId: String?) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"

        viewModelScope.launch {
            val request = OrderRequest(statusId)
            _orders.value = apiService.getOrders(authToken, request)
        }
    }
}