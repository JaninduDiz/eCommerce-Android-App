package com.example.shoppingapp.session

import android.content.Context
import android.content.SharedPreferences
import com.example.shoppingapp.models.User

class UserSessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString("userName", user.userName)
            putString("emailAddress", user.emailAddress)
            putString("addressLine1", user.addressLine1)
            putString("addressLine2", user.addressLine2)
            putString("city", user.city)
            putString("postalCode", user.postalCode)
            apply()
        }
    }

    fun getUser(): User? {
        return User(
            userName = prefs.getString("userName", "") ?: "",
            emailAddress = prefs.getString("emailAddress", "") ?: "",
            addressLine1 = prefs.getString("addressLine1", "") ?: "",
            addressLine2 = prefs.getString("addressLine2", "") ?: "",
            city = prefs.getString("city", "") ?: "",
            postalCode = prefs.getString("postalCode", "") ?: ""
        )
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
