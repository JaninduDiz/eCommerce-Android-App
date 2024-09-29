package com.example.shoppingapp.models

import androidx.compose.runtime.MutableState


data class OrderItem(
    val product: Product,
    val quantity: MutableState<Int>,
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
