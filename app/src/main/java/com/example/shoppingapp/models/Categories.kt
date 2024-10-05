
package com.example.shoppingapp.models

data class Category(
    val id: String,
    val name: String,
    val isActive: Boolean,
    val products: List<Product>
)

val officeFurnitureCategory = Category(
    id = "1",                   // Correct property name
    name = "Office Furniture",
    isActive = true,             // Assuming these categories are active
    products = emptyList()       // Empty list for products
)

val livingRoomFurnitureCategory = Category(
    id = "2",
    name = "Living Room Furniture",
    isActive = true,
    products = emptyList()
)

val diningFurnitureCategory = Category(
    id = "3",
    name = "Dining Furniture",
    isActive = true,
    products = emptyList()
)

val bedroomFurnitureCategory = Category(
    id = "4",
    name = "Bedroom Furniture",
    isActive = true,
    products = emptyList()
)

val gamingFurnitureCategory = Category(
    id = "5",
    name = "Gaming Furniture",
    isActive = true,
    products = emptyList()
)
