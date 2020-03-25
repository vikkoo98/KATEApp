package com.kateproject.kateapp

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.PersistableBundle
import android.os.Process.*
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
open class InitializeTask( private val mainActivity: MainActivity): AsyncTask <Void, Void, Boolean>() { //ez itt sír valamiért
    override fun doInBackground(vararg params: Void?): Boolean {
        setThreadPriority(THREAD_PRIORITY_BACKGROUND + THREAD_PRIORITY_MORE_FAVORABLE)
        //beállítások betöltése
        ModelPreferencesManager.with(mainActivity)
        try
        {
            val saveFile = ModelPreferencesManager.get<SaveFile>("KEY_SAVE")!!
            MainActivity.settings = saveFile.settings
        }
        catch (e: Exception) {
            for (x in MainActivity.settings.checkBoxArray.indices) {
                MainActivity.settings.checkBoxArray[x] = true
            }
        }

        //cikkek beszerzése
        val comm = Communicator()
        try
        {
            //cikkek betöltése fájlból
            val articles = mutableListOf<Article>()
            val saveFile = ModelPreferencesManager.get<SaveFile>("KEY_SAVE")!!
            val tArticles = saveFile.articles
            println("Talált cikkek: " +tArticles.size)
            println(tArticles[0]!!.title.rendered)
            val nArticles = comm.loadArticles(10, forceLoad = true)
            for (x in nArticles.indices)
            {
                if (nArticles[x].id != tArticles[0]!!.id) articles.add(nArticles[x])
                else {break}
            }
            for (x in tArticles.indices)
            {
                articles.add(tArticles[x]!!)
            }
            MainActivity.gArticles =articles
        }
        catch (e: Exception)
        {
            println(e)
            //cikkek első betöltése
            val articles = mutableListOf<Article>()
            var atEnd = false
            var i = 0
            while (!atEnd)      //addig fusson amíg talál új cikket
            {
                val tArticles = comm.loadArticles(MainActivity.settings.arNum, forceLoad = true,diff = i* MainActivity.settings.arNum)
                if (tArticles.count() <= 0) { atEnd = true }
                else {articles.addAll(tArticles)}
                i++
                println("i = $i, talát cikkek: ${tArticles.count()}")
            }
            MainActivity.gArticles =articles
        }

        val authors = comm.loadAuthors()

        //írónevek és id-k összefésülése
        for (x in MainActivity.gArticles.indices)
            for (y in authors.indices)
            {
                if (MainActivity.gArticles[x].author == authors[y].id)
                    MainActivity.gArticles[x].authorName = authors[y].name
            }

        //értesítés deklarálás
        MainActivity.jobScheduler = mainActivity.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val cn = ComponentName(mainActivity ,BackgroundScheduler::class.java)
        val builder: JobInfo.Builder = JobInfo.Builder(128,cn)

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        //{
        //    builder.setPeriodic(30*60*1000,60*1000)
        //}
        //else
        //{
            builder.setPeriodic(30*60*1000)
        //}

        val pBundle = PersistableBundle()
        pBundle.putInt("ARTICLE_ID", MainActivity.gArticles[0].id)

        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setPersisted(true)
        builder.setExtras(pBundle)

        MainActivity.jobInfo = builder.build()

        if (MainActivity.settings.arNot)
        {
            MainActivity.jobScheduler.schedule(MainActivity.jobInfo)
        }
        else
        { MainActivity.jobScheduler.cancel(128) }

        return true
    }

}