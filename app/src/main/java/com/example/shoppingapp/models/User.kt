package com.example.shoppingapp.models

data class LoginResponse(
    val id: String,
    val username: String,
    val email: String,
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: Int,
    val firstName: String,
    val lastName: String,
    val address: String,
    val phoneNumber: String,
    val gender: Int
)

data class User(
    val id: String,
    val username: String,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val address: String?,
    val phoneNumber: String?,
)

data class UpdateUserRequest(
    val username: String?,
    val email: String?,
    val role: Int?,
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val phoneNumber: String?,
)