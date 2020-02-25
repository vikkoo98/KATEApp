package com.kateproject.kateapp

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch

enum class MessageType {
    BUG, KATE, ARTICLE
}

enum class ArticleType {
    IRODALOM, CIKK, HIR, INTERJU
}

class Communicator
{
    fun LoadArticles(Count: Int,diff: Int=0):List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=$Count"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        var articles: List<Article> = emptyList()

        val countDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException) {
                /* Snackbar.make(view, "Loading failed, try again", Snackbar.LENGTH_SHORT)
                     .show()*/
                println("JSON download Error: $e")
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gsonInstance = GsonBuilder().create()
                articles = gsonInstance.fromJson(body, Array<Article>::class.java).toList()
                for (x in 0 until articles.count()) { articles[x].authorName=""}
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()
        return articles
    }
fun LoadAuthors(count:Int = 50): List<Author>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/users?per_page=$count"
        var authors: List<Author> = emptyList()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        val countDownLatch = CountDownLatch(1)
        client.newCall(request).enqueue(object : Callback
        {
            override fun onFailure(call: Call, e: IOException) {
                /* Snackbar.make(view, "Loading failed, try again", Snackbar.LENGTH_SHORT)
                     .show()*/
                println("JSON download Error: $e")
                countDownLatch.countDown()
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gsonInstance = GsonBuilder().create()
                authors = gsonInstance.fromJson(body, Array<Author>::class.java).toList()
                countDownLatch.countDown()
            }
        })
        countDownLatch.await()

        return authors
    }
    fun SearchArticles(sourceArticles: List<Article>, query: String): List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=50"
        var articles = mutableListOf<Article>()
        for (x in 0 until sourceArticles.count()-1)
        {
            if (sourceArticles[x].title.rendered.contains(query,true))
            {
                articles.add(sourceArticles[x])
            }
        }
        return articles
    }
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