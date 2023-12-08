package com.example.restauratio.register

data class RegisterRequest(
    val email: String,
    val password: String,
    val passwordAgain: String,
    val firstName: String?,
    val lastName: String?,
    val address: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val phone: String
)
