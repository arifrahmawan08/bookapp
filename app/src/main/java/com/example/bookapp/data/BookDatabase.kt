package com.example.bookapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class, User::class], version = 2)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_db"
                )
                    .fallbackToDestructiveMigration() // hapus DB lama kalau versi berubah
                    .build()
                    .also { instance = it }
            }
        }
    }
}
