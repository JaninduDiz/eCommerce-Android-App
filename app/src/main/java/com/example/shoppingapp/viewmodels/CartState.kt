package com.example.shoppingapp.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.CartItem
import com.example.shoppingapp.models.Product


class CartState {
    val items: SnapshotStateList<CartItem> = mutableStateListOf()

    // Function to add a product to the cart
    fun addToCart(product: Product) {
        val existingItem = items.find { it.product.productId == product.productId }
        if (existingItem != null) {
            existingItem.quantity.value += 1
        } else {
            items.add(CartItem(product))
        }
    }

    // Function to remove a product from the cart
    fun removeFromCart(product: Product) {
        val existingItem = items.find { it.product.productId == product.productId }
        if (existingItem != null) {
            items.remove(existingItem)
        }
    }

    // Function to increase quantity
    fun increaseQuantity(product: Product) {
        val existingItem = items.find { it.product.productId == product.productId }
        existingItem?.quantity?.value = (existingItem?.quantity?.value ?: 0) + 1
    }

    // Function to decrease quantity
    fun decreaseQuantity(product: Product) {
        val existingItem = items.find { it.product.productId == product.productId }
        existingItem?.quantity?.let {
            if (it.value > 1) {
                it.value -= 1
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun getTotalAmount(): Double {
        var total = 0.0
        for (item in items) {
            total += item.product.price * item.quantity.value
        }
        return String.format("%.2f", total).toDouble()
    }

    // Function to clear the cart
    fun clearCart() {
        items.clear()
    }
}
