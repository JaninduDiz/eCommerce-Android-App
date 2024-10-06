// ApiService.kt
package com.example.shoppingapp.Services

import com.example.shoppingapp.models.Category
import com.example.shoppingapp.models.Order
import com.example.shoppingapp.models.OrderRequest
import com.example.shoppingapp.models.Product
import com.example.shoppingapp.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val id: String,
    val username: String,
    val email: String,
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: Int,
    val firstName: String,
    val lastName: String,
    val address: String,
    val phoneNumber: String,
    val gender: Int
)

data class UpdateUserRequest(
    val username: String?,
    val email: String?,
    val role: Int?,
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val phoneNumber: String?,
)

data class Vendor(
    val id: String,
    val name: String,
    val productIds: List<String>,
    val averageRating: Float,
    val totalRatings: Int
)


data class Rating(
    val id: String,
    val vendorId: String,
    val customerId: String,
    val stars: Int,
    val comment: String
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
    suspend fun getRatingsByVendorId(@Path("vendorId") vendorId: String): Response<List<Rating>>

    @GET("Vendor/{vendorId}")
    suspend fun getVendorById(@Path("vendorId") vendorId: String): Response<Vendor>

}