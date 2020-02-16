package com.kateproject.kateapp

class Article(
    val id: Int,            //cikk ID
    val date: String,       //kiadás dátuma
    val link: String,       //URL
    val title: Packed,      //cím
    val content: Packed,    //tartalom
    val excerpt: Packed,    //összefoglaló
    val author: Int,        //szerző kód
    val authorName: String  //szerzőnév
)

class Packed(val rendered: String)
        //erre azért van szükség, hogy a JSON-t megfelelően kezeljük
