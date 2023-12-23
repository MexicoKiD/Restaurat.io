package com.example.restauratio.profile.aboutme

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
class UserViewModel @Inject constructor(
    private val authService: AuthService,
    private val sessionManager: SessionManager
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

    fun updateUserData(userData: UserDataModel) {
        viewModelScope.launch {
            authService.updateUserData(userData)
            _userData.postValue(userData)
        }
    }
}
