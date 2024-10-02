package com.example.shoppingapp.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime


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
    var note: String?,
    val createdAt: LocalDateTime
)

@RequiresApi(Build.VERSION_CODES.O)
val sampleOrders = listOf(
    Order(
        id = "order_1727715349666",
        customerId = "customer_123",
        items = listOf(
            OrderItem(
                product = Product(
                    productId = "9",
                    name = "Coffee Table",
                    category = Category(categoryId = "2", name = "Living Room Furniture"),
                    vendorId = "vendor4",
                    isActive = true,
                    price = 129.99,
                    description = "Modern coffee table with glass top and wooden legs.",
                    stock = 9
                ),
                quantity = 2,
                isDelivered = false
            ),
            OrderItem(
                product = Product(
                    productId = "10",
                    name = "Dining Chair",
                    category = Category(categoryId = "3", name = "Dining Room Furniture"),
                    vendorId = "vendor5",
                    isActive = true,
                    price = 79.99,
                    description = "Comfortable dining chair with cushioned seat.",
                    stock = 15
                ),
                quantity = 4,
                isDelivered = false
            ),
            OrderItem(
                product = Product(
                    productId = "10",
                    name = "Dining Chair",
                    category = Category(categoryId = "3", name = "Dining Room Furniture"),
                    vendorId = "vendor5",
                    isActive = true,
                    price = 79.99,
                    description = "Comfortable dining chair with cushioned seat.",
                    stock = 15
                ),
                quantity = 3,
                isDelivered = false
            ),
            OrderItem(
                product = Product(
                    productId = "10",
                    name = "Dining Chair",
                    category = Category(categoryId = "3", name = "Dining Room Furniture"),
                    vendorId = "vendor5",
                    isActive = true,
                    price = 79.99,
                    description = "Comfortable dining chair with cushioned seat.",
                    stock = 15
                ),
                quantity = 1,
                isDelivered = false
            )

        ),
        status = 1,
        cancellationReason = null,
        note = "Deliver to the front porch.",
        createdAt = LocalDateTime.of(2022, 1, 1, 10, 0)
    ),
    Order(
        id = "order_1727715349667",
        customerId = "customer_456",
        items = listOf(
            OrderItem(
                product = Product(
                    productId = "20",
                    name = "King Size Bed",
                    category = Category(categoryId = "4", name = "Bedroom Furniture"),
                    vendorId = "vendor3",
                    isActive = true,
                    price = 999.99,
                    description = "King size bed with upholstered headboard and wooden frame.",
                    stock = 4
                ),
                quantity = 5,
                isDelivered = true
            ),
            OrderItem(
                product = Product(
                    productId = "21",
                    name = "Nightstand",
                    category = Category(categoryId = "4", name = "Bedroom Furniture"),
                    vendorId = "vendor3",
                    isActive = true,
                    price = 129.99,
                    description = "Modern nightstand with drawer and open shelf.",
                    stock = 10
                ),
                quantity = 1,
                isDelivered = true
            )
        ),
        status = 2,
        cancellationReason = null,
        note = "Deliver to the second floor.",
        createdAt = LocalDateTime.of(2022, 1, 2, 10, 0)
    )
)