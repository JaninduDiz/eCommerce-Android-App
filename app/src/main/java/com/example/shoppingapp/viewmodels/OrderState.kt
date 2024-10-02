package com.example.shoppingapp.viewmodels

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem
import java.time.LocalDateTime

class OrderState {

    private val orders = SnapshotStateList<Order>() // Store all orders here

    private val currentOrder = mutableStateOf<Order?>(null)

    // Create a new order from the cart state
    @RequiresApi(Build.VERSION_CODES.O)
    fun generateOrder(cartState: CartState, customerId: String, note: String? = null): Order {
        val orderItems = cartState.items.map { cartItem ->
            OrderItem(
                product = cartItem.product,
                quantity = cartItem.quantity.value,
                isDelivered = false
            )
        }
        Log.d(TAG, "GenerateOrder: Order items: $orderItems")
        val order = Order(
            id = generateOrderId(),  // remove this
            customerId = customerId,
            items = orderItems,
            status = 1,
            cancellationReason = null,
            note = note,
            createdAt = LocalDateTime.now()
        )

        orders.add(order)

        currentOrder.value = order
        return order
    }

    // Get the current order
    fun getCurrentOrder(): Order? {
        return currentOrder.value
    }

    // Fetch an order by its ID from the orders list
    fun getOrderById(orderId: String): Order? {
        return orders.find { it.id == orderId }
    }

    // Helper function to generate a unique order ID
    private fun generateOrderId(): String {
        return "order_" + System.currentTimeMillis()
    }

    // Clear the current order
    fun clearOrder() {
        currentOrder.value = null
    }
}