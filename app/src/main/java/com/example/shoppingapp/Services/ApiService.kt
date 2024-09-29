// ApiService.kt
package com.example.shoppingapp.Services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val message: String, val token: String?)
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse> // Updated to return LoginResponse
}
