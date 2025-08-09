package com.example.bookapp.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookapp.utils.SessionManager

@Composable
fun LoginScreen(navController: NavHostController, context: Context) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (session.isUserValid(username, password)) {
                    navController.navigate("book_list") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    error = "Username atau password salah"
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }

        TextButton(
            onClick = { navController.navigate("register") }
        ) {
            Text("Belum punya akun? Daftar")
        }

        if (error.isNotEmpty()) {
            Text(error, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
