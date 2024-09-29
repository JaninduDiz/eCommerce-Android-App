package com.example.shoppingapp.models

data class User(
    val userName: String,
    val emailAddress: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val postalCode: String
)