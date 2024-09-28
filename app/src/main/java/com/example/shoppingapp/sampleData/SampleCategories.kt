
package com.example.shoppingapp.sampleData

data class Category(
    val categoryId: String,
    val name: String
)

val officeFurnitureCategory = Category(
    categoryId = "1",
    name = "Office Furniture"
)

val livingRoomFurnitureCategory = Category(
    categoryId = "2",
    name = "Living Room Furniture"
)

val diningFurnitureCategory = Category(
    categoryId = "3",
    name = "Dining Furniture"
)

val bedroomFurnitureCategory = Category(
    categoryId = "4",
    name = "Bedroom Furniture"
)

val gamingFurnitureCategory = Category(
    categoryId = "5",
    name = "Gaming Furniture"
)