package com.kateproject.kateapp.ui.home
import com.kateproject.kateapp.MainActivity.Companion.gArticles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kateproject.kateapp.Article

class HomeViewModel: ViewModel() {

    private val _text = MutableLiveData<List<Article>>().apply {
        //val comm = Communicator()
        //val articles = comm.LoadArticles(5)
        value = gArticles
        /*listOf(
            Article(10,"2011-11-11","www.kamu.hu", Packed("elsocikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela"),
            Article(11,"2011-11-11","www.kamu.hu", Packed("masodikcikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela")
        )*/
    }
    val text: LiveData<List<Article>> = _text
}