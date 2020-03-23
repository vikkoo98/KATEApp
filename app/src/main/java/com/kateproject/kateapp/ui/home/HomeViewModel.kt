package com.kateproject.kateapp.ui.home
import com.kateproject.kateapp.MainActivity.Companion.gArticles
import com.kateproject.kateapp.MainActivity.Companion.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kateproject.kateapp.Article
import com.kateproject.kateapp.ArticleType
import com.kateproject.kateapp.Communicator

class HomeViewModel: ViewModel() {

    private val _text = MutableLiveData<List<Article>>().apply {
        val comm = Communicator()
        var types = mutableListOf<ArticleType>()

        if (settings.checkBoxArray[0]) {types.add(ArticleType.CIKK)}
        if (settings.checkBoxArray[1]) {types.add(ArticleType.ELET)}
        if (settings.checkBoxArray[2]) {types.add(ArticleType.OKTATAS)}
        if (settings.checkBoxArray[3]) {types.add(ArticleType.VERSENY)}
        if (settings.checkBoxArray[4]) {types.add(ArticleType.HIREK)}
        if (settings.checkBoxArray[5]) {types.add(ArticleType.INTERJU)}
        if (settings.checkBoxArray[6]) {types.add(ArticleType.KATV)}
        if (settings.checkBoxArray[7]) {types.add(ArticleType.KEPRIPORT)}
        if (settings.checkBoxArray[8]) {types.add(ArticleType.KULTURA)}
        if (settings.checkBoxArray[9]) {types.add(ArticleType.KIALLITAS)}
        if (settings.checkBoxArray[10]) {types.add(ArticleType.KONYV)}
        if (settings.checkBoxArray[11]) {types.add(ArticleType.MOZI)}
        if (settings.checkBoxArray[12]) {types.add(ArticleType.SPORT)}
        if (settings.checkBoxArray[13]) {types.add(ArticleType.SZINHAZ)}
        if (settings.checkBoxArray[14]) {types.add(ArticleType.ZENE)}
        if (settings.checkBoxArray[15]) {types.add(ArticleType.PALYAZAT)}
        if (settings.checkBoxArray[16]) {types.add(ArticleType.PROGRAM)}
        if (settings.checkBoxArray[17]) {types.add(ArticleType.FESZTIVAL)}
        if (settings.checkBoxArray[18]) {types.add(ArticleType.KONCERT)}
        if (settings.checkBoxArray[19]) {types.add(ArticleType.PARTY)}
        if (settings.checkBoxArray[20]) {types.add(ArticleType.RHELY)}
        if (settings.checkBoxArray[21]) {types.add(ArticleType.NOKOMMENT)}
        if (settings.checkBoxArray[22]) {types.add(ArticleType.UNCATEGORIZED)}
        if (settings.checkBoxArray[23]) {types.add(ArticleType.VIDEO)}

        value = comm.FilterArticles(gArticles,types)
        /*listOf(
            Article(10,"2011-11-11","www.kamu.hu", Packed("elsocikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela"),
            Article(11,"2011-11-11","www.kamu.hu", Packed("masodikcikk"), Packed("szoveg"), Packed("osszefoglalo"),410,"Bela")
        )*/
    }
    val text: LiveData<List<Article>> = _text
}