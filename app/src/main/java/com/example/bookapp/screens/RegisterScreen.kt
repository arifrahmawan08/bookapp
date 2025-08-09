package com.example.bookapp.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bookapp.utils.SessionManager

@Composable
fun RegisterScreen(navController: NavHostController, context: Context) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Register", style = MaterialTheme.typography.headlineMedium)
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

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Konfirmasi Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                when {
                    username.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
                        error = "Semua field wajib diisi"
                    }
                    password != confirmPassword -> {
                        error = "Password tidak cocok"
                    }
                    else -> {
                        session.saveUser(username, password)
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Register")
        }

        if (error.isNotEmpty()) {
            Text(error, color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
