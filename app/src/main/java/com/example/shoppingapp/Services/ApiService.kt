// ApiService.kt
package com.example.shoppingapp.Services

import com.example.shoppingapp.models.Category
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderItem
import com.example.shoppingapp.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(
    val id: String,
    val username: String,
    val email: String
)
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: Int
)

data class OrderRequest(
    val customerId: String,
    val items: List<OrderItem>,
    val status: Int,
    val cancellationReason: String?,
    val note: String?,
    val totalValue: Double
)


interface ApiService {
    @POST("User/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @GET("Product/active")
    suspend fun getActiveProducts(): Response<List<Product>>

    @GET("ProductCategory")
    suspend fun getCategories(): Response<List<Category>>

    @GET("Product/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    @POST("Order")
    suspend fun createOrder(@Body request: OrderRequest): Response<Order>

    @GET("Order/customer/{customerId}")
    suspend fun getOrdersByCustomerId(@Path("customerId") customerId: String): Response<List<Order>>

}