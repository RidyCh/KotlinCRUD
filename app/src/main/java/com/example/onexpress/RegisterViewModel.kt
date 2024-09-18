package com.example.onexpress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onexpress.data.entity.User
import com.example.onexpress.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel(private val userRepository: UserRepository): ViewModel(){
    private val _isEmailUsed=MutableLiveData<Boolean>()
    val isEmailUsed: LiveData<Boolean>get()=_isEmailUsed
    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess:LiveData<Boolean>get()=_registrationSuccess

    fun checkEmail(email:String){
        viewModelScope.launch(Dispatchers.IO){
            val emailAlreadyUsed=userRepository.isEmailUsed(email)
            withContext(Dispatchers.Main){
                _isEmailUsed.value = emailAlreadyUsed

            }

        }
    }

    fun registerUser(user: User){
        viewModelScope.launch(Dispatchers.IO){

            userRepository.insert(user)
            withContext(Dispatchers.Main){
                _registrationSuccess.value=true
            }
        }
    }
}