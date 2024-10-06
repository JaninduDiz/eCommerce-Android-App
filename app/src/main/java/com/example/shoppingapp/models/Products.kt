package com.example.shoppingapp.models

data class Product(
    val productId: String,
    val name: String,
    val category: String,
    val vendorId: String,
    val isActive: Boolean,
    val price: Double,
    val description: String?,
    val stock: Int,
    val imageUrls: List<String>
)
