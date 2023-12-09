package com.example.restauratio.loginSession

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor() : ViewModel() {

    private val _authToken = MutableLiveData<String?>()
    val authToken: LiveData<String?> get() = _authToken

    private val _tokenExpirationTime = MutableLiveData<Long?>()
    val tokenExpirationTime: LiveData<Long?> get() = _tokenExpirationTime

    fun setAuthToken(token: String?, expirationTime: Long?) {
        _authToken.value = token
        _tokenExpirationTime.value = expirationTime
    }
}