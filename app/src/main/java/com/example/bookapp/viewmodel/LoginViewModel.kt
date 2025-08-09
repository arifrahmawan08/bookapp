package com.example.bookapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.BookDatabase
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = BookDatabase.getDatabase(application).userDao()

    fun loginUser(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = userDao.getUserByUsername(username)
            if (user != null && user.password == password) {
                onResult(true, "Login berhasil")
            } else {
                onResult(false, "Username/password salah")
            }
        }
    }
}
