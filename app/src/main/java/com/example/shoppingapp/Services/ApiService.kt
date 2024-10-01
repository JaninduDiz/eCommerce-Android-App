// ApiService.kt
package com.example.shoppingapp.Services

//import com.example.shoppingapp.models.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET


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

data class Products(
    val productId: String,
    val name: String,
    val category: String,
    val vendorId: String,
    val isActive: Boolean,
    val price: Double,
    val description: String,
    val stock: Int
)




interface ApiService {
    @POST("User/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("User/register")
    suspend fun register(@Body request: RegisterRequest): Response<LoginResponse>

    @GET("Product/active")
    suspend fun getActiveProducts(): Response<List<Products>>

}
