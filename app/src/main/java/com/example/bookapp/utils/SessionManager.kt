package com.example.bookapp.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String) {
        prefs.edit()
            .putString("USERNAME", username)
            .putString("PASSWORD", password)
            .apply()
    }

    fun getUsername(): String? {
        return prefs.getString("USERNAME", null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.contains("USERNAME") && !getUsername().isNullOrEmpty()
    }

    fun isUserValid(username: String, password: String): Boolean {
        val savedUsername = prefs.getString("USERNAME", null)
        val savedPassword = prefs.getString("PASSWORD", null)
        return username == savedUsername && password == savedPassword
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
