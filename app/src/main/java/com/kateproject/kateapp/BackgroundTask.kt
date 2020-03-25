package com.kateproject.kateapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.text.Html


//ez végzi a cikk megtalálását
open class BackgroundExecute(val id: Int): AsyncTask<Void, Void, Article?>()
{
    override fun doInBackground(vararg p0: Void?): Article? {
        val comm = Communicator()
        val articles = comm.loadArticles(1, forceLoad = true)

        return when {
            articles.isEmpty() -> {//ha nincs net pl.
                println("hiba a letöltéssel")
                null //Article(0, "", "", Packed("hiba a letöltéssel"), emptyList(), Packed(id.toString()), Packed(id.toString()), 0, "", emptyList())
            }
            id == 0 -> {//ha nincs id

                println("nem megy át az id")
                null //Article(0, "", "", Packed(articles[0].title.rendered), emptyList(), Packed("$id - ${articles[0]}"), Packed("$id - ${articles[0]}"), 0, "", emptyList())
            }
            id != articles[0].id -> {//ha van új cikk
                println("van új cikk")
                articles[0]
            }
            id == articles[0].id -> {//ha nincs új cikk
                println("nincs új cikk")
                null //Article(0, "", "", Packed(articles[0].title.rendered), emptyList(), Packed("$id - ${articles[0]}"), Packed("$id - ${articles[0]}"), 0, "", emptyList())
            }
            else -> //egyéb esetben
                null //Article(0, "", "", Packed("de vagyok"), emptyList(), Packed("$id "), Packed("$id - ${articles[0]}"), 0, "", emptyList())
        }
    }
}

//ez jeleníti meg értesítésként
class BackgroundScheduler: JobService() {

    private lateinit var backgroundExecute: BackgroundExecute
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationTask: NotificationTask

    override fun onStopJob(p0: JobParameters?): Boolean {

        backgroundExecute.cancel(true)
        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        val pBundle = p0?.extras
        val id = pBundle?.getInt("ARTICLE_ID") ?: 0
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationTask = NotificationTask("com.kateproject.kateapp", "Általános értesítések" ,this,notificationManager)

        println("de, ide is eljutottam")

        backgroundExecute = @SuppressLint("StaticFieldLeak")
        object : BackgroundExecute(id) {

            override fun onPostExecute(result: Article?) {
                super.onPostExecute(result)
                if (result != null) {

                    val title: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        Html.fromHtml(result.title.rendered, Html.FROM_HTML_MODE_COMPACT).toString()
                    } else {
                        Html.fromHtml(result.title.rendered).toString()
                    }

                    val excerpt: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    {
                        Html.fromHtml(result.excerpt.rendered, Html.FROM_HTML_MODE_COMPACT).toString()
                    } else {
                        Html.fromHtml(result.excerpt.rendered).toString()
                    }

                    notificationTask.sendNotification(title, excerpt)
                }
                jobFinished(p0,false)
            }
        }
        backgroundExecute.execute()
        return true
    }

}