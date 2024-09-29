package com.example.shoppingapp.models

data class User(
    val userName: String,
    val emailAddress: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val postalCode: String
)

val sampleUser = User(
    userName = "john_doe",
    emailAddress = "john.doe@example.com",
    addressLine1 = "123 Main St",
    addressLine2 = "Apt 4B",
    city = "Springfield",
    postalCode = "12345"
)