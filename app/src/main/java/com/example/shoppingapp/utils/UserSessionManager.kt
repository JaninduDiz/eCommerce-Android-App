package com.example.shoppingapp.session

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppingapp.models.User

class UserSessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString("userId", user.id)
            putString("userName", user.userName)
            putString("email", user.email)
            putString("address", user.address)
            putString("phoneNumber", user.phoneNumber)
            putString("firstName", user.firstName)
            putString("lastName", user.lastName)
            apply()
        }
    }

    fun getUser(): User {
        return User(
            userName = prefs.getString("userName", "") ?: "",
            email = prefs.getString("email", "") ?: "",
            address = prefs.getString("address", "") ?: "",
            phoneNumber = prefs.getString("phoneNumber", "") ?: "",
            firstName = prefs.getString("firstName", "") ?: "",
            lastName = prefs.getString("lastName", "") ?: "",
            id = prefs.getString("userId", "") ?: ""
        )
    }

    fun updateUser(updatedUser: User) {
        prefs.edit().apply {
            putString("userName", updatedUser.userName)
            putString("email", updatedUser.email)
            putString("address", updatedUser.address)
            putString("phoneNumber", updatedUser.phoneNumber)
            putString("firstName", updatedUser.firstName)
            putString("lastName", updatedUser.lastName)
            apply()
        }
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun logout() {
        with(prefs.edit()) {
            clear() // Clear all data
            apply()
        }
    }
}
