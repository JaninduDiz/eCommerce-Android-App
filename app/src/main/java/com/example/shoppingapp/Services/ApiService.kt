// ApiService.kt
package com.example.shoppingapp.Services

import com.example.shoppingapp.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

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



interface ApiService {
    @POST("User/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @GET("Product/active")
    suspend fun getActiveProducts(): Response<List<Product>>

}