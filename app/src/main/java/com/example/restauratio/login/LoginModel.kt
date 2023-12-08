package com.example.restauratio.login

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val email: String,
    val password: String
)