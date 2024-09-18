package com.example.onexpress.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Cari Game..."
    }
    val text: LiveData<String> = _text
}