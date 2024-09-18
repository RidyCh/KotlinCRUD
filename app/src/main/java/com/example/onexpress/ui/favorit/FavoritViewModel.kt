package com.example.onexpress.ui.favorit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoritViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favorit Fragment"
    }
    val text: LiveData<String> = _text
}