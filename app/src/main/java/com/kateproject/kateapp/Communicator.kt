package com.kateproject.kateapp

import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.concurrent.CountDownLatch
import com.kateproject.kateapp.MainActivity.Companion.gArticles
import java.lang.Exception

enum class MessageType {
    BUG, KATE, ARTICLE
}

class Communicator
{
    fun LoadArticles(Count: Int,diff: Int=0, forceLoad: Boolean = false):List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=$Count"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        var articles: List<Article> = emptyList()

        if (forceLoad) {

            var numTries = 5
            while (numTries > 0)
            {
                val countDownLatch = CountDownLatch(1)
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("JSON download Error: $e")
                        countDownLatch.countDown()
                        numTries--
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                            val body = response.body?.string()
                            println(body)

                            val gsonInstance = GsonBuilder().create()
                            articles =
                                gsonInstance.fromJson(body, Array<Article>::class.java).toList()
                            for (x in articles.indices) {
                                if (articles[x].title.rendered.contains("interjú", true)) {
                                    articles[x].type = ArticleType.INTERJU
                                } else {
                                    articles[x].type = ArticleType.CIKK
                                }
                                articles[x].authorName = ""
                            }
                            numTries=0
                        } catch (e: Exception) {
                            println("$numTries: Error:")
                            println(e)
                            numTries--
                        }
                        countDownLatch.countDown()
                    }
                })
                countDownLatch.await()
            }
        }
        else
        {
            var numTries = 5
            while (numTries > 0)
            {
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println("JSON download Error: $e")
                    }

                    override fun onResponse(call: Call, response: Response) {

                        try {
                            val body = response.body?.string()
                            println(body)

                            val gsonInstance = GsonBuilder().create()
                            articles =
                                gsonInstance.fromJson(body, Array<Article>::class.java).toList()
                            for (x in articles.indices) {
                                if (articles[x].title.rendered.contains("interjú", true)) {
                                    articles[x].type = ArticleType.INTERJU
                                } else {
                                    articles[x].type = ArticleType.CIKK
                                }
                                articles[x].authorName = ""
                                gArticles=articles
                                numTries=0
                            }
                        } catch (e: Exception) {
                            println("$numTries: Error:")
                            println(e)
                            numTries--
                        }

                    }
                })
            }
        }

        return articles
    }
    fun LoadAuthors(count:Int = 50): List<Author>
    {

        val url = "http://www.kate.hu/wp-json/wp/v2/users?per_page=$count"
        var authors: List<Author> = emptyList()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()


        var numTries = 5
        while (numTries > 0)
        {
            val countDownLatch = CountDownLatch(1)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("JSON download Error: $e")
                    numTries--
                    countDownLatch.countDown()
                }

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string()
                        println(body)
                        val gsonInstance = GsonBuilder().create()
                        authors = gsonInstance.fromJson(body, Array<Author>::class.java).toList()
                        numTries=0
                    } catch (e: Exception) {
                        println("$numTries: Error:")
                        println(e)
                        numTries--
                    }
                    countDownLatch.countDown()
                }
            })
            countDownLatch.await()
        }
        return authors
    }

    fun SearchArticles(sourceArticles: List<Article>, query: String): List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=50"
        val articles = mutableListOf<Article>()
        for (x in 0 until sourceArticles.count())
        {
            if (sourceArticles[x].title.rendered.contains(query,true))
            {
                articles.add(sourceArticles[x])
            }
        }
        return articles
    }

  fun FilterArticles(sourceArticles: List<Article>, types: List<ArticleType>): List<Article>
  {
      println(types)
      val articles = mutableListOf<Article>()
      for (x in 0 until sourceArticles.count())
          for (y in 0 until types.count())
          {
              if (sourceArticles[x].type == types[y])
                  articles.add(sourceArticles[x])
          }
      return articles
  }

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