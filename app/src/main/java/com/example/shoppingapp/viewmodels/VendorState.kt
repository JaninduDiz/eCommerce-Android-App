package com.example.shoppingapp.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.Rating
import com.example.shoppingapp.models.User

class VendorState {
    // Map to store cached vendor details by vendor ID
    var vendorDetails: MutableMap<String, User?> = mutableStateMapOf()

    // Map to store cached products by vendor ID
    var products: MutableMap<String, List<Product>> = mutableStateMapOf()

    // Map to store cached ratings by vendor ID
    var ratings: MutableMap<String, List<Rating>> = mutableStateMapOf()

    // Map to store cached customer names by customer ID (used for displaying ratings)
    var customerNames: MutableMap<String, String> = mutableStateMapOf()

    // Function to clean all the cached data
    fun clearVendorState() {
        vendorDetails.clear()
        products.clear()
        ratings.clear()
        customerNames.clear()
    }
}