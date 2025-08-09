package com.example.bookapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.Book
import com.example.bookapp.data.BookDatabase
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val bookDao = BookDatabase.getDatabase(application).bookDao()
    val books = bookDao.getAllBooks()

    fun addBook(book: Book) = viewModelScope.launch {
        bookDao.insert(book)
    }

    fun updateBook(book: Book) = viewModelScope.launch {
        bookDao.update(book)
    }

    fun deleteBook(book: Book) = viewModelScope.launch {
        bookDao.delete(book)
    }
}