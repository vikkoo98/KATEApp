package com.kateproject.kateapp

data class Article(
    val id: Int,            //cikk ID
    val date: String,       //kiadás dátuma
    val link: String,       //URL
    val title: Packed,      //cím
    val content: Packed,    //tartalom
    val excerpt: Packed,    //összefoglaló
    val author: Int,        //szerző kód
    var authorName: String="noAuthor"  //szerzőnév
)

data class Packed(val rendered: String)
//erre azért van szükség, hogy a JSON-t megfelelően kezeljük

