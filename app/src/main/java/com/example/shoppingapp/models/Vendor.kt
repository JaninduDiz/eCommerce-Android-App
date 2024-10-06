package com.example.shoppingapp.models

data class Rating(
    val id: String,
    val vendorId: String,
    val customerId: String,
    val stars: Int,
    val comment: String
)