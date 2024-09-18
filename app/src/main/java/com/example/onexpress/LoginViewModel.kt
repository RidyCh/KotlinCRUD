package com.example.onexpress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onexpress.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _authSuccess=MutableLiveData<Boolean>()
    val authSuccess: LiveData<Boolean> get()=_authSuccess

    fun auth(email:String,password:String){

        viewModelScope.launch(Dispatchers.IO){
            val auth=userRepository.auth(email,password)
            withContext(Dispatchers.Main){
                _authSuccess.value=auth
            }
        }
    }

}