package com.kateproject.kateapp

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.text.Html
import com.kateproject.kateapp.MainActivity.Companion.gArticles


//ez végzi a cikk megtalálását
open class BackgroundExecute: AsyncTask<Void, Void, Article?>()
{
    companion object {
        lateinit var lastArticle: Article
    }

    override fun doInBackground(vararg p0: Void?): Article? {
        val comm = Communicator()
        val articles = comm.LoadArticles(1, forceLoad = true)

        return if (articles.isEmpty()) {//ha nincs net pl.
            println("hiba a letöltéssel")
            Article(
                0,
                "",
                "",
                Packed("hiba a letöltéssel"),
                emptyList(),
                Packed(gArticles[0].title.rendered),
                Packed(gArticles[0].title.rendered),
                0,
                "",
                emptyList()
            )
        } else if (gArticles !=null && gArticles.isNotEmpty() && articles[0].id == gArticles[0].id) {//ha nincs új cikk
            lastArticle = gArticles[0]
            println("nincs új cikk")
            Article(
                0,
                "",
                "",
                Packed(articles[0].title.rendered),
                emptyList(),
                Packed(gArticles[0].title.rendered),
                Packed(gArticles[0].title.rendered),
                0,
                "",
                emptyList()
            )
        } else if ( gArticles !=null && gArticles.isNotEmpty() && articles[0].id != gArticles[0].id) {//ha van új cikk
            lastArticle = gArticles[0]
            articles[0]
        } else if ((gArticles == null || gArticles.isEmpty() ) && articles[0].id == lastArticle.id) {//ha már nincs gArticles
            println("nincs új cikk")
            Article(
                0,
                "",
                "",
                Packed(articles[0].title.rendered),
                emptyList(),
                Packed(lastArticle.title.rendered),
                Packed(lastArticle.title.rendered),
                0,
                "",
                emptyList()
            )
        } else if ((gArticles == null || gArticles.isEmpty() ) && articles[0].id != lastArticle.id) {//ha már nincs gArticles, és van új cikk
            articles[0]
        } else {//egyéb esetben
            null
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
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationTask = NotificationTask("com.kateproject.kateapp", "Általános értesítések" ,this,notificationManager)

        backgroundExecute = @SuppressLint("StaticFieldLeak")
        object : BackgroundExecute() {

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