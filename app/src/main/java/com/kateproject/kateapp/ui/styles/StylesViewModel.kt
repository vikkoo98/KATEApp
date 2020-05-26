package com.kateproject.kateapp.ui.styles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StylesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value=""
    }
    val text: LiveData<String> = _text
}