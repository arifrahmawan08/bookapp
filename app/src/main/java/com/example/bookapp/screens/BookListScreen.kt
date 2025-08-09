package com.example.bookapp.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.bookapp.utils.SessionManager
import com.example.bookapp.viewmodel.BookViewModel
import com.example.bookapp.viewmodel.BookViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel(
        factory = BookViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daftar Buku") },
                actions = {
                    IconButton(onClick = {
                        sessionManager.clearSession()
                        navController.navigate("login") {
                            popUpTo("book_list") { inclusive = true }
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add_book")
            }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(books) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("edit_book/${book.id}")
                        }
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        AsyncImage(
                            model = book.imageUrl,
                            contentDescription = "Book Image",
                            modifier = Modifier.size(60.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Harga: Rp${book.price}")
                        }
                    }
                }
            }
        }
    }
}
