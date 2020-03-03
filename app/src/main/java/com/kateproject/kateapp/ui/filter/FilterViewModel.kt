package com.kateproject.kateapp.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Jelöld be azokat a típusokat, amik érdekelnek"
    }
    val text: LiveData<String> = _text
}