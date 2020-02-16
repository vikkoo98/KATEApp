package com.kateproject.kateapp

enum class MessageType {
    BUG, KATE, ARTICLE
}

enum class ArticleType {
    IRODALOM, CIKK, HIR, INTERJU
}

class Communicator
{
    fun LoadArticles(Count: Int,diff: Int=0): List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=50"

        val Articles: List<Article>
        Articles = emptyList() //ideiglenesen
        return Articles
    }
//
//  fun SearchArticles(): List<Article>
//  {
//      val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=50"
//
//      val Articles: List<Article>
//      return Articles
//  }
//  fun FilterArticles(Type: ArticleType): List<Article>
//  {
//      val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=50"
//
//      val Articles: List<Article>
//      return Articles
//  }

    fun GetArchive(Count: Int,diff: Int=0)
    {
        //ennek majd kell valamiféle return érték
    }

    fun SendMessage(Email: String, Name: String, Content: String, Type: MessageType, ArticleID: Int = 0)
    {
        //nem kell return érték, de lehet
    }

    fun GetEvents(Count: Int, diff: Int=0)
    {
        //ennek majd kell valamiféle return érték
    }
}