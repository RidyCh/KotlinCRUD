package com.example.onexpress.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.onexpress.data.entity.User

@Dao
interface UserDao{
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("Delete FROM users")
    fun deleteAll():Int

    @Query("SELECT * FROM users WHERE email= :email")
    fun getEmail(email:String):User?

    @Query("SELECT * FROM users WHERE email= :email AND password= :password")
    fun auth(email: String,password:String):User?


}