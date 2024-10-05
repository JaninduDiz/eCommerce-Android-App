package com.example.shoppingapp.models

data class Vendor(
    val vendorId: String,
    val name: String,
    val imageUrl: String,
    val avgRating: Float,
    val totalReviews: Int,
    val products: List<Product>
)