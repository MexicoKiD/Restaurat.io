package com.example.restauratio.loginSession

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.restauratio.profile.UserDataModel
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

    fun getUserEmail(): String {
        return sharedPreferences.getString("user_email", "") ?: ""
    }

    fun saveUserData(userDataModel: UserDataModel) {
        sharedPreferences.edit {
            putString("user_email", userDataModel.email)
            putString("user_firstname", userDataModel.firstName)
            putString("user_lastname", userDataModel.lastName)
            putString("user_address", userDataModel.address)
            putString("user_city", userDataModel.city)
            putString("user_phone", userDataModel.phone)
            putString("user_postalCode", userDataModel.postalCode)
        }
    }

    fun getUserData(): UserDataModel {
        return UserDataModel(
            sharedPreferences.getString("user_address", "") ?: "",
            sharedPreferences.getString("user_city", "") ?: "",
            sharedPreferences.getString("user_email", "") ?: "",
            sharedPreferences.getString("user_firstname", "") ?: "",
            sharedPreferences.getString("user_lastname", "") ?: "",
            sharedPreferences.getString("user_phone", "") ?: "",
            sharedPreferences.getString("user_postalCode", "") ?: "",

        )
    }
}