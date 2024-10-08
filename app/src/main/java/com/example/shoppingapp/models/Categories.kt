
package com.example.shoppingapp.models

data class Category(
    val id: String,
    val name: String,
    val isActive: Boolean,
    var products: List<Product>
)
