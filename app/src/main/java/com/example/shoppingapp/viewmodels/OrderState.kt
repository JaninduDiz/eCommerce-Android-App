package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem

class OrderState {

    private val currentOrder = mutableStateOf<Order?>(null)

    // Create a new order from the cart state
    fun generateOrder(cartState: CartState, customerId: String, note: String? = null): Order {
        val orderItems = cartState.items.map { cartItem ->
            OrderItem(
                product = cartItem.product,
                quantity = cartItem.quantity,
                isDelivered = false  // Default value as the order is not delivered yet
            )
        }

        val order = Order(
            id = generateOrderId(),  // You can replace this with a real ID generator
            customerId = customerId,
            items = orderItems,
            status = 1,  // Assuming 1 means "Pending"
            cancellationReason = null,
            note = note
        )

        currentOrder.value = order
        return order
    }

    // Get the current order (if any)
    fun getCurrentOrder(): Order? {
        return currentOrder.value
    }

    // Helper function to generate a random order ID
    private fun generateOrderId(): String {
        return "order_" + System.currentTimeMillis()
    }

    // Clear the current order
    fun clearOrder() {
        currentOrder.value = null
    }
}