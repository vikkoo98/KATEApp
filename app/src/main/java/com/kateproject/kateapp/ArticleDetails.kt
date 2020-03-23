package com.kateproject.kateapp

enum class ArticleType {
    CIKK, ELET, OKTATAS, VERSENY, HIREK, INTERJU, KATV, KEPRIPORT, KULTURA, KIALLITAS, KONYV, MOZI, SPORT,
    SZINHAZ, ZENE, PALYAZAT, PROGRAM, FESZTIVAL, KONCERT, PARTY, RHELY, NOKOMMENT, UNCATEGORIZED, VIDEO
}
data class Article(
    val id: Int,            //cikk ID
    val date: String,       //kiadás dátuma
    val link: String,       //URL
    val title: Packed,      //cím
    val categories: List<Int>,  //kategóriák
    val content: Packed,    //tartalom
    val excerpt: Packed,    //összefoglaló
    val author: Int,        //szerző kód
    var authorName: String = "noAuthor",  //szerzőnév
    var tipus: List<ArticleType> //cikk típusa
)

data class Author(
    val id: Int,
    val name: String
)
/*
data class Category(
    val id: Int,
    val name: String
)
*/
data class Packed(val rendered: String)
//erre azért van szükség, hogy a JSON-t megfelelően kezeljük

