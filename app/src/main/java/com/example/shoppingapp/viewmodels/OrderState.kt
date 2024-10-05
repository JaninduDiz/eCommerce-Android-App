package com.example.shoppingapp.viewmodels

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem
import com.example.shoppingapp.models.OrderRequest

class OrderState {

    private val orders = SnapshotStateList<OrderRequest>()

    private val currentOrder = mutableStateOf<Order?>(null)

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateOrder(cartState: CartState, customerId: String, note: String? = null, totalAmount: Double, deliveryCharge: Double): OrderRequest {
        val orderItems = cartState.items.map { cartItem ->
            OrderItem(
                productId = cartItem.product.productId,
                quantity = cartItem.quantity.value,
                unitPrice = cartItem.product.price,
                vendorId = cartItem.product.vendorId,
                isDelivered = false
            )
        }
        val order = OrderRequest(
            customerId = customerId,
            items = orderItems,
            status = 0,
            cancellationReason = "",
            note = note,
            totalValue = calculateOrderTotal(totalAmount, deliveryCharge)
        )
        Log.d(TAG, "Order generated successfully: ${order.toString()}")
        orders.add(order)
        return order
    }

    // Get the current order
    fun getCurrentOrder(): Order? {
        return currentOrder.value
    }

    // Fetch an order by its ID from the orders list
//    fun getOrderById(orderId: String): Order? {
//        return orders.find { it.id == orderId }
//    }

    // Helper function to generate a unique order ID
    private fun generateOrderId(): String {
        return "order_" + System.currentTimeMillis()
    }

    //calculate orderTotal
    private fun calculateOrderTotal(currentTotal: Double, deliveryCharge: Double): Double {
        var total = 0.0
        total += currentTotal + deliveryCharge

        return total
    }

    // Clear the current order
    fun clearOrder() {
        currentOrder.value = null
    }
}