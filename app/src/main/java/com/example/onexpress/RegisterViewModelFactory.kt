package com.example.onexpress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onexpress.data.dao.UserDao
import com.example.onexpress.data.repository.UserRepository

class RegisterViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(UserRepository(userDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}