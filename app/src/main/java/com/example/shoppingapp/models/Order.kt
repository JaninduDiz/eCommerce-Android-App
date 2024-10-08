package com.example.shoppingapp.models

data class OrderItem(
    val productId: String,
    val vendorId: String,
    val quantity: Int,
    val isDelivered: Boolean,
    val unitPrice: Double
)

data class OrderRequest(
    val customerId: String,
    val items: List<OrderItem>,
    val status: Int,
    val cancellationReason: String?,
    val note: String?,
    val totalValue: Double
)

data class Order(
    val id: String,
    val customerId: String,
    var items: List<OrderItem>,
    var status: Int,
    var cancellationReason: String?,
    var note: String?,
    var totalValue: Double,
    val createdAt: String
)
