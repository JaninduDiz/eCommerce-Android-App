// ApiService.kt
package com.example.shoppingapp.services

import com.example.shoppingapp.models.Category
import com.example.shoppingapp.models.LoginRequest
import com.example.shoppingapp.models.LoginResponse
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderRequest
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.RegisterRequest
import com.example.shoppingapp.models.Review
import com.example.shoppingapp.models.ReviewRequest
import com.example.shoppingapp.models.UpdateUserRequest
import com.example.shoppingapp.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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

    @GET("Order/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<Order>

    @GET("User/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<User>

    @PUT("User/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: UpdateUserRequest
    ): Response<User>

    @GET("Rating/vendor/{vendorId}")
    suspend fun getRatingsByVendorId(@Path("vendorId") vendorId: String): Response<List<Review>>

    @GET("Product/vendor/{vendorId}")
    suspend fun getVendorProducts(@Path("vendorId") vendorId: String): Response<List<Product>>

    @POST("Rating")
    suspend fun addRating(@Body review: ReviewRequest): Response<String>

    @PUT("Order/{id}")
    suspend fun updateOrder(@Path("id") id: String, @Body order: Order): Response<Order>
}