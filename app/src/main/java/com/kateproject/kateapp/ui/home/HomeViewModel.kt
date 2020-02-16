package com.kateproject.kateapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kateproject.kateapp.Article
import com.kateproject.kateapp.Packed

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<List<Article>>().apply {
        value = listOf(
            Article(10,"2011-11-11","www.kamu.hu", Packed("elsocikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela"),
            Article(11,"2011-11-11","www.kamu.hu", Packed("masodikcikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela")

        )
    }
    val text: LiveData<List<Article>> = _text
}