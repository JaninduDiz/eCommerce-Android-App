package com.example.shoppingapp.models

data class User(
    val id: String,
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val address: String?,
    val phoneNumber: String?,
)
