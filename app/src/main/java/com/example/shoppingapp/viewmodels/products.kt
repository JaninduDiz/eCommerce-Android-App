package com.example.shoppingapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.Services.ApiService
import com.example.shoppingapp.Services.Products
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductsViewModel(private val apiService: ApiService) : ViewModel() {
    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products: StateFlow<List<Products>> get() = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            val response: Response<List<Products>> = apiService.getActiveProducts()
            if (response.isSuccessful) {
                _products.value = response.body() ?: emptyList()
            } else {
                // Handle error here (e.g., log or show a message)
            }
        }
    }
}
