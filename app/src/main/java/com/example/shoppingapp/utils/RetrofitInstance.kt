package com.example.shoppingapp.utils

import com.example.shoppingapp.Services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://ecommerceapp2-young-silence-7292.fly.dev/api/") // Replace with your localhost IP if running on a real device
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
