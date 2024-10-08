
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

// API service Interface to define the API endpoints

interface ApiService {

    //login endpoint
    @POST("User/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    //register endpoint
    @POST("User/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    //get all products endpoint
    @GET("Product/active")
    suspend fun getActiveProducts(): Response<List<Product>>

    //get all categories endpoint
    @GET("ProductCategory")
    suspend fun getCategories(): Response<List<Category>>

    //get product by id endpoint
    @GET("Product/{id}")
    suspend fun getProductById(@Path("id") id: String): Response<Product>

    //create order endpoint
    @POST("Order")
    suspend fun createOrder(@Body request: OrderRequest): Response<Order>

    //get orders by customer id endpoint
    @GET("Order/customer/{customerId}")
    suspend fun getOrdersByCustomerId(@Path("customerId") customerId: String): Response<List<Order>>

    //get order by order id endpoint
    @GET("Order/{id}")
    suspend fun getOrderById(@Path("id") id: String): Response<Order>

    //get user by id endpoint
    @GET("User/{id}")
    suspend fun getUserById(@Path("id") id: String): Response<User>

    //update user endpoint
    @PUT("User/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Body request: UpdateUserRequest
    ): Response<User>

    //get ratings by vendor id endpoint
    @GET("Rating/vendor/{vendorId}")
    suspend fun getRatingsByVendorId(@Path("vendorId") vendorId: String): Response<List<Review>>

    //get products by vendor id endpoint
    @GET("Product/vendor/{vendorId}")
    suspend fun getVendorProducts(@Path("vendorId") vendorId: String): Response<List<Product>>

    //add rating endpoint
    @POST("Rating")
    suspend fun addRating(@Body review: ReviewRequest): Response<String>

    //update order endpoint
    @PUT("Order/{id}")
    suspend fun updateOrder(@Path("id") id: String, @Body order: Order): Response<Order>
}