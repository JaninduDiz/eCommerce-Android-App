package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem

class OrderState {

    // A list of all orders in the system
    private val orders = SnapshotStateList<Order>()

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

        // Store the new order
        orders.add(order)
        currentOrder.value = order
        return order
    }

    // Get the current order (if any)
    fun getCurrentOrder(): Order? {
        return currentOrder.value
    }

    // Get an order by its ID
    fun getOrderById(orderId: String): Order? {
        return orders.find { it.id == orderId }
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