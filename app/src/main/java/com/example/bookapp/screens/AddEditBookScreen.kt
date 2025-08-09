package com.example.bookapp.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.bookapp.data.Book
import com.example.bookapp.viewmodel.BookViewModel

@Composable
fun AddEditBookScreen(
    navController: NavHostController,
    viewModel: BookViewModel = viewModel(),
    bookId: Int? = null
) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    val bookToEdit = books.find { it.id == bookId }

    var title by remember { mutableStateOf(bookToEdit?.title ?: "") }
    var price by remember { mutableStateOf(bookToEdit?.price?.toString() ?: "") }
    var imageUrl by remember { mutableStateOf(bookToEdit?.imageUrl ?: "") }

    // Launcher untuk memilih gambar dari galeri
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            imageUrl = it.toString()
        }
    }

    LazyColumn {
        item {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul Buku") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Harga Buku") })

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("Pilih Gambar dari Galeri")
                }

                if (imageUrl.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Button(onClick = {
                        val book = Book(
                            id = bookToEdit?.id ?: 0,
                            title = title,
                            price = price.toIntOrNull() ?: 0,
                            imageUrl = imageUrl
                        )

                        if (bookToEdit == null) {
                            viewModel.addBook(book)
                        } else {
                            viewModel.updateBook(book)
                        }
                        navController.popBackStack()
                    }) {
                        Text(if (bookToEdit == null) "Tambah" else "Simpan")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (bookToEdit != null) {
                        Button(onClick = {
                            viewModel.deleteBook(bookToEdit)
                            navController.popBackStack()
                        }) {
                            Text("Hapus")
                        }
                    }
                }
            }
        }
    }
}
