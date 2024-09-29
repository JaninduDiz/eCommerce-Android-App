package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Product

// Data class representing a Cart Item
data class CartItem(val product: Product, var quantity: MutableState<Int> = mutableIntStateOf(1))

// CartState to manage cart items
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

    // Function to clear the cart
    fun clearCart() {
        items.clear()
    }
}