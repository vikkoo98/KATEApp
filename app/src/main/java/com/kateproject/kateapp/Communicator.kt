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
    private var tries = 10
    fun loadArticles(Count: Int, diff: Int=0, forceLoad: Boolean = false):List<Article>
    {
        val url = "http://www.kate.hu/wp-json/wp/v2/posts?per_page=$Count&offset=$diff"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        var articles: List<Article> = emptyList()

        if (forceLoad) {

            var numTries = tries
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

                                //ez most ide hardcode-olva lett, le lehetne ezt is mindig kérni, de azért nem akartam,
                                //hogy ne kelljen feleslegesen még call-okat küldeni, minden esetre nem elegáns
                                val tTipus = mutableListOf<ArticleType>()
                                if (articles[x].title.rendered.contains("interjú", true)) tTipus.add(ArticleType.INTERJU)
                                if (articles[x].categories.contains(7)) tTipus.add(ArticleType.CIKK)
                                if (articles[x].categories.contains(13)) tTipus.add(ArticleType.ELET)
                                if (articles[x].categories.contains(16)) tTipus.add(ArticleType.FESZTIVAL)
                                if (articles[x].categories.contains(171)) tTipus.add(ArticleType.KATV)
                                if (articles[x].categories.contains(28)) tTipus.add(ArticleType.KEPRIPORT)
                                if (articles[x].categories.contains(80)) tTipus.add(ArticleType.KIALLITAS)
                                if (articles[x].categories.contains(27)) tTipus.add(ArticleType.KONCERT)
                                if (articles[x].categories.contains(75)) tTipus.add(ArticleType.KONYV)
                                if (articles[x].categories.contains(25)) tTipus.add(ArticleType.KULTURA)
                                if (articles[x].categories.contains(26)) tTipus.add(ArticleType.MOZI)
                                if (articles[x].categories.contains(6)) tTipus.add(ArticleType.NOKOMMENT)
                                if (articles[x].categories.contains(29)) tTipus.add(ArticleType.OKTATAS)
                                if (articles[x].categories.contains(37)) tTipus.add(ArticleType.PALYAZAT)
                                if (articles[x].categories.contains(11)) tTipus.add(ArticleType.PARTY)
                                if (articles[x].categories.contains(12)) tTipus.add(ArticleType.PROGRAM)
                                if (articles[x].categories.contains(5)) tTipus.add(ArticleType.RHELY)
                                if (articles[x].categories.contains(108)) tTipus.add(ArticleType.SPORT)
                                if (articles[x].categories.contains(35)) tTipus.add(ArticleType.SZINHAZ)
                                if (articles[x].categories.contains(1)) tTipus.add(ArticleType.UNCATEGORIZED)
                                if (articles[x].categories.contains(97)) tTipus.add(ArticleType.VERSENY)
                                if (articles[x].categories.contains(14)) tTipus.add(ArticleType.VIDEO)
                                if (articles[x].categories.contains(36)) tTipus.add(ArticleType.ZENE)
                                if (tTipus.size == 0) { tTipus.add(ArticleType.UNCATEGORIZED) }
                                articles[x].tipus=tTipus

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
            var numTries = tries
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

                                //ez most ide hardcode-olva lett, le lehetne ezt is mindig kérni, de azért nem akartam,
                                //hogy ne kelljen feleslegesen még call-okat küldeni, minden esetre nem elegáns
                                val tTipus = mutableListOf<ArticleType>()
                                if (articles[x].title.rendered.contains("interjú", true)) tTipus.add(ArticleType.INTERJU)
                                if (articles[x].categories.contains(7)) tTipus.add(ArticleType.CIKK)
                                if (articles[x].categories.contains(13)) tTipus.add(ArticleType.ELET)
                                if (articles[x].categories.contains(16)) tTipus.add(ArticleType.FESZTIVAL)
                                if (articles[x].categories.contains(171)) tTipus.add(ArticleType.KATV)
                                if (articles[x].categories.contains(28)) tTipus.add(ArticleType.KEPRIPORT)
                                if (articles[x].categories.contains(80)) tTipus.add(ArticleType.KIALLITAS)
                                if (articles[x].categories.contains(27)) tTipus.add(ArticleType.KONCERT)
                                if (articles[x].categories.contains(75)) tTipus.add(ArticleType.KONYV)
                                if (articles[x].categories.contains(25)) tTipus.add(ArticleType.KULTURA)
                                if (articles[x].categories.contains(26)) tTipus.add(ArticleType.MOZI)
                                if (articles[x].categories.contains(6)) tTipus.add(ArticleType.NOKOMMENT)
                                if (articles[x].categories.contains(29)) tTipus.add(ArticleType.OKTATAS)
                                if (articles[x].categories.contains(37)) tTipus.add(ArticleType.PALYAZAT)
                                if (articles[x].categories.contains(11)) tTipus.add(ArticleType.PARTY)
                                if (articles[x].categories.contains(12)) tTipus.add(ArticleType.PROGRAM)
                                if (articles[x].categories.contains(5)) tTipus.add(ArticleType.RHELY)
                                if (articles[x].categories.contains(108)) tTipus.add(ArticleType.SPORT)
                                if (articles[x].categories.contains(35)) tTipus.add(ArticleType.SZINHAZ)
                                if (articles[x].categories.contains(1)) tTipus.add(ArticleType.UNCATEGORIZED)
                                if (articles[x].categories.contains(97)) tTipus.add(ArticleType.VERSENY)
                                if (articles[x].categories.contains(14)) tTipus.add(ArticleType.VIDEO)
                                if (articles[x].categories.contains(36)) tTipus.add(ArticleType.ZENE)
                                if (tTipus.size == 0) { tTipus.add(ArticleType.UNCATEGORIZED) }
                                articles[x].tipus=tTipus

                                articles[x].authorName = ""
                            }
                            gArticles=articles
                            numTries=0
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

    fun loadAuthors(count:Int = 50): List<Author>
    {

        val url = "http://www.kate.hu/wp-json/wp/v2/users?per_page=$count"
        var authors: List<Author> = emptyList()
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()


        var numTries = tries
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

    fun searchArticles(sourceArticles: List<Article>, query: String): List<Article>
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

    fun filterArticles(sourceArticles: List<Article>, types: List<ArticleType>): List<Article>
    {
          println(types)
          val articles = mutableListOf<Article>()
          var vanE: Boolean
          for (x in 0 until sourceArticles.count()) {
              vanE = false
              for (y in 0 until types.count()) {
                  if (sourceArticles[x].tipus.contains(types[y]))
                      vanE = true
              }
              if (vanE) articles.add(sourceArticles[x])
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