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


val sampleProducts = listOf(
    // Office Furniture
    Product(
        productId = "1",
        name = "Office Chair",
        category = "Office Furniture",
        vendorId = "vendor1",
        isActive = true,
        price = 145.69,
        description = "Ergonomic office chair with adjustable height and lumbar support.",
        stock = 10,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "6",
        name = "Office Desk",
        category = "Office Furniture",
        vendorId = "vendor1",
        isActive = true,
        price = 199.99,
        description = "Spacious office desk with cable management and storage drawers.",
        stock = 7,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "11",
        name = "Filing Cabinet",
        category = "Office Furniture",
        vendorId = "vendor1",
        isActive = true,
        price = 89.99,
        description = "Steel filing cabinet with three drawers and a lock.",
        stock = 15,
        imageUrls = listOf("url1", "url2")
    ),
    // Living Room Furniture
    Product(
        productId = "4",
        name = "Leather Sofa",
        category = "Living Room Furniture",
        vendorId = "vendor4",
        isActive = true,
        price = 899.99,
        description = "Luxurious leather sofa with reclining seats and cup holders.",
        stock = 2,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "9",
        name = "Coffee Table",
        category = "Living Room Furniture",
        vendorId = "vendor4",
        isActive = true,
        price = 129.99,
        description = "Modern coffee table with glass top and wooden legs.",
        stock = 9,
        imageUrls = listOf("url1", "url2")
    ),
    // Dining Furniture
    Product(
        productId = "5",
        name = "Dining Table Set",
        category = "Dining Furniture",
        vendorId = "vendor5",
        isActive = true,
        price = 599.99,
        description = "Modern dining table set with 6 cushioned chairs.",
        stock = 8,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "17",
        name = "Dining Chair",
        category = "Dining Furniture",
        vendorId = "vendor5",
        isActive = true,
        price = 99.99,
        description = "Elegant dining chair with padded seat and backrest.",
        stock = 20,
        imageUrls = listOf("url1", "url2")
    ),
    // Bedroom Furniture
    Product(
        productId = "20",
        name = "King Size Bed",
        category = "Bedroom Furniture",
        vendorId = "vendor3",
        isActive = true,
        price = 999.99,
        description = "King size bed with upholstered headboard and wooden frame.",
        stock = 4,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "21",
        name = "Nightstand",
        category = "Bedroom Furniture",
        vendorId = "vendor3",
        isActive = true,
        price = 129.99,
        description = "Modern nightstand with drawer and open shelf.",
        stock = 10,
        imageUrls = listOf("url1", "url2")
    ),
    // Gaming Furniture
    Product(
        productId = "2",
        name = "Gaming Desk",
        category = "Gaming Furniture",
        vendorId = "vendor2",
        isActive = true,
        price = 249.99,
        description = "Spacious gaming desk with RGB lighting and cable management.",
        stock = 5,
        imageUrls = listOf("url1", "url2")
    ),
    Product(
        productId = "7",
        name = "Gaming Chair",
        category = "Gaming Furniture",
        vendorId = "vendor2",
        isActive = true,
        price = 179.99,
        description = "Comfortable gaming chair with adjustable armrests and lumbar support.",
        stock = 12,
        imageUrls = listOf("url1", "url2")
    )
)