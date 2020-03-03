package com.kateproject.kateapp

enum class ArticleType {
    IRODALOM, CIKK, HIR, INTERJU
}
data class Article(
    val id: Int,            //cikk ID
    val date: String,       //kiadás dátuma
    val link: String,       //URL
    val title: Packed,      //cím
    val content: Packed,    //tartalom
    val excerpt: Packed,    //összefoglaló
    val author: Int,        //szerző kód
    var authorName: String = "noAuthor",  //szerzőnév
    var type: ArticleType = ArticleType.CIKK //cikk típusa
)

data class Author(
    val id: Int,
    val name: String
)

data class Packed(val rendered: String)
//erre azért van szükség, hogy a JSON-t megfelelően kezeljük

