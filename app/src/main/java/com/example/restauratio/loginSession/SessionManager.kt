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
    private val EXPIRATION_TIME_KEY = "expiration_time"
    private val LOGGED_IN_USER_ID_KEY = "logged_in_user_id"
    private val USER_EMAIL = "user_email"

    fun saveAuthToken(token: String, expirationTime: Long) {
        sessionViewModel.setAuthToken(token, expirationTime)
        sharedPreferences.edit()
            .putString(AUTH_TOKEN_KEY, token)
            .putLong(EXPIRATION_TIME_KEY, expirationTime)
            .apply()
    }

    fun getAuthToken(): LiveData<String?> {
        val authToken = sharedPreferences.getString(AUTH_TOKEN_KEY, null)
        val expirationTime = getExpirationTime()
        sessionViewModel.setAuthToken(authToken, expirationTime)
        return sessionViewModel.authToken
    }

    private fun getExpirationTime(): Long {
        return sharedPreferences.getLong(EXPIRATION_TIME_KEY, 0)
    }

    private fun clearAuthToken() {
        sessionViewModel.setAuthToken(null, 0)
        sharedPreferences.edit().remove(AUTH_TOKEN_KEY).apply()
        sharedPreferences.edit().remove(EXPIRATION_TIME_KEY).apply()
    }

    fun isLoggedIn(): Boolean {
        return getExpirationTime() > System.currentTimeMillis()
    }

    fun logout() {
        clearAuthToken()
    }

    fun saveUserEmail(email: String) {
        sharedPreferences.edit().putString(USER_EMAIL, email).apply()
    }

    fun getUserEmail(): String {
        return sharedPreferences.getString(USER_EMAIL, "") ?: ""
    }
    fun saveLoggedInUserId(userId: Int) {
        sharedPreferences.edit().putInt(LOGGED_IN_USER_ID_KEY, userId).apply()
    }

    fun getLoggedInUserId(): Int {
        return sharedPreferences.getInt(LOGGED_IN_USER_ID_KEY, -1)
    }
}