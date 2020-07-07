package com.kateproject.kateapp

/*-------------------------------------------------------
---------------------------------------------------------
Nem a legelegánsabb, de HardCode-olva vannak a cikkek típusai.
Ezt lehetne valahogy automatikusan csinálni, de lusta? voltam.
---------------------------------------------------------
---------------------------------------------------------*/
enum class ArticleType {
    CIKK, ELET, OKTATAS, VERSENY, HIREK, INTERJU, KATV, KEPRIPORT, KULTURA, KIALLITAS, KONYV, MOZI, SPORT,
    SZINHAZ, ZENE, PALYAZAT, PROGRAM, FESZTIVAL, KONCERT, PARTY, RHELY, NOKOMMENT, UNCATEGORIZED, VIDEO
}



/*-------------------------------------------------------
---------------------------------------------------------
Ebben tárolja a program az adott cikkeket. Nem biztos, hogy
ennyi mindent el kéne tenni, de nem foglal sok helyet, szóval
mért ne...
---------------------------------------------------------
---------------------------------------------------------*/
data class Article(
    val id: Int,            //cikk ID
    val date: String,       //kiadás dátuma
    val link: String,       //URL
    val title: Packed,      //cím
    val categories: List<Int>,  //kategóriák
    val content: Packed,    //tartalom
    val excerpt: Packed,    //összefoglaló
    val author: Int,        //szerző kód
    var new: Boolean = false,
    var authorName: String = "noAuthor",  //szerzőnév
    var tipus: List<ArticleType> //cikk típusa
)



/*-------------------------------------------------------
---------------------------------------------------------
Mivel a weboldalon ID-vel vannak a cikkírók, így ezeket külön
el kell tárolni, és összefésülni.
---------------------------------------------------------
---------------------------------------------------------*/
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



/*-------------------------------------------------------
---------------------------------------------------------
erre azért van szükség, hogy a JSON-t megfelelően kezeljük,
a neve teljesen irreleváns...
---------------------------------------------------------
---------------------------------------------------------*/
data class Packed(val rendered: String)

