package com.example.restauratio.loginSession

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import javax.inject.Inject



class SessionManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application
) : ViewModelStoreOwner {

    override val viewModelStore = ViewModelStore()

    private val sessionViewModel: SessionViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[SessionViewModel::class.java]
    }

    private val AUTH_TOKEN_KEY = "auth_token"

    fun saveAuthToken(token: String) {
        sessionViewModel.setAuthToken(token)
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun getAuthToken(): LiveData<String?> {
        val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, null)
        sessionViewModel.setAuthToken(authToken)
        return sessionViewModel.authToken
    }

    private fun clearAuthToken() {
        sessionViewModel.setAuthToken(null)
        sharedPreferences.edit().remove(AUTH_TOKEN_KEY).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null) != null
    }

    fun logout() {
        clearAuthToken()
    }
}