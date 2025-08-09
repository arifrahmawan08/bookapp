package com.example.bookapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookapp.screens.*
import com.example.bookapp.utils.SessionManager

@Composable
fun AppNavigation(navController: NavHostController) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }

    val start = if (session.isLoggedIn()) "book_list" else "login"

    NavHost(navController = navController, startDestination = start) {
        composable("login") { LoginScreen(navController, context) }
        composable("register") { RegisterScreen(navController, context) }
        composable("book_list") { BookListScreen(navController) }
        composable("add_book") { AddEditBookScreen(navController) }
        composable("edit_book/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            AddEditBookScreen(navController, bookId = bookId)
        }
    }
}
