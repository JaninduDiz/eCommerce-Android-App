package com.example.shoppingapp.models

data class Review(
    val id: String,
    val vendorId: String,
    val customerId: String,
    val stars: Int,
    val comment: String
)

data class ReviewRequest(
    val vendorId: String,
    val customerId: String,
    val stars: Int,
    val comment: String
)