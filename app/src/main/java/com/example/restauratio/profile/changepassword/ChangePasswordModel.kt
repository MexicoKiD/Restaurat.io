package com.example.restauratio.profile.changepassword

data class ChangePasswordModel(
    val email: String,
    val password: String,
    val passwordAgain: String
)

data class ChangePasswordResponse (val token: String?)