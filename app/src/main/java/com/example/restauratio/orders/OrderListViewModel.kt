package com.example.restauratio.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class OrderListViewModel @Inject constructor(
    private val orderApi: AuthService,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _orderListLiveData = MutableLiveData<List<Order>>()
    val orderListLiveData: LiveData<List<Order>> get() = _orderListLiveData


    fun getOrderList(statusId: Int?) {
        val authToken = "Bearer ${sessionManager.getAuthToken().value.orEmpty()}"
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                orderApi.getOrderList(authToken, OrderListRequest(statusId ?: 0))
            }
            if (response.isSuccessful) {
                _orderListLiveData.value = response.body()
                Log.e("OrderListViewModel", "user data $response")
            } else {
                Log.e("OrderListViewModel", "Error updating user data $response")
            }
        }
    }
}