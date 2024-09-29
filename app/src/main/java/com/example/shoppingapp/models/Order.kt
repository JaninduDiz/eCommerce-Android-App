package com.example.shoppingapp.models


data class OrderItem(
    val product: Product,
    val quantity: Int,
    val isDelivered: Boolean
)

data class Order(
    val id: String,
    val customerId: String,
    val items: List<OrderItem>,
    val status: Int,
    val cancellationReason: String?,
    val note: String?
)

// Example Order data
val sampleOrders = listOf(
    Order(
        id = "66f5381108275778f6bbe36d",
        customerId = "b1b2c3d4e5f60718293a4b5c",
        items = listOf(
            OrderItem(
                product = Product(
                    productId = "b5129079-9bb7-4beb-96b2-a6dc4a1c40c8",
                    name = "Gaming Chair",
                    category = Category(categoryId = "1", name = "Gaming Furniture"),
                    vendorId = "b1b2c3d4e5f60718293a4b5c",
                    isActive = true,
                    price = 249.99,
                    description = "Comfortable gaming chair with adjustable armrests and lumbar support.",
                    stock = 50
                ),
                quantity = 210,
                isDelivered = true
            ),
            OrderItem(
                product = Product(
                    productId = "b5129079-9bb7-4beb-96b2-a7dc4a1c40c0",
                    name = "Office Desk",
                    category = Category(categoryId = "2", name = "Office Furniture"),
                    vendorId = "b1b2c3d4e5f60718293a4b5c",
                    isActive = true,
                    price = 199.99,
                    description = "Spacious office desk with cable management and storage drawers.",
                    stock = 100
                ),
                quantity = 10,
                isDelivered = false
            )
        ),
        status = 2,
        cancellationReason = "",
        note = "Please handle with care."
    )
)