package com.example.onexpress.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.onexpress.data.dao.GameDao
import com.example.onexpress.data.dao.UserDao
import com.example.onexpress.data.entity.Game
import com.example.onexpress.data.entity.User


@Database(entities = [User::class, Game::class], version = 1)
abstract class MyDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java,
                    "app-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}