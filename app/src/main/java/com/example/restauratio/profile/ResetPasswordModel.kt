package com.example.restauratio.profile

data class ResetPasswordModel(
    val email: String,
    val password: String,
    val passwordAgain: String
)

data class ResetPasswordResponse (val token: String?)