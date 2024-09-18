package com.example.onexpress.data.repository

import com.example.onexpress.data.dao.UserDao
import com.example.onexpress.data.entity.User

class UserRepository(private val dao: UserDao) {
    val users = dao.getAllUsers()
    suspend fun insert(user: User){
        return dao.insert(user)
    }

    suspend fun getEmail(email:String): User?{
        return dao.getEmail(email)
    }

    suspend fun isEmailUsed(email: String): Boolean {
        return dao.getEmail(email) != null
    }

    suspend fun auth(email:String,password:String):Boolean{
        return dao.auth(email,password)!=null
    }

}