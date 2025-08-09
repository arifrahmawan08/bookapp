package com.example.bookapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.BookDatabase
import com.example.bookapp.data.User
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao = BookDatabase.getDatabase(application).userDao()

    fun registerUser(username: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val existingUser = userDao.getUserByUsername(username)
            if (existingUser != null) {
                onResult(false, "Username sudah digunakan")
            } else {
                userDao.insertUser(User(username = username, password = password))
                onResult(true, "Registrasi berhasil")
            }
        }
    }
}
