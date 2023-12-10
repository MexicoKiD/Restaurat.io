package com.example.restauratio.login

data class LoginModel(
    val email: String,
    val password: String
)

data class LoginResponse(val token: String)