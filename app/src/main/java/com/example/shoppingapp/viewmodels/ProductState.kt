package com.example.shoppingapp.viewmodels


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.shoppingapp.models.Product

class ProductState  {
    val products: SnapshotStateList<Product> = mutableStateListOf()

    // Function to add a product
    fun addProduct(product: Product) {
        products.add(product)
    }

    // Function to add a list of products at once
    fun addProducts(productList: List<Product>) {
        products.addAll(productList)
    }

    // Function to remove a product
    fun removeProduct(product: Product) {
        products.remove(product)
    }

    // Function to get a product by its ID
    fun getProductById(productId: String): Product? {
        return products.find { it.productId == productId }
    }

    // Function to clear all products
    fun clearProducts() {
        products.clear()
    }


}