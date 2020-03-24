package com.kateproject.kateapp

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object {
        var gArticles: List<Article> = emptyList()
        var settings = Settings(checkBoxArray = BooleanArray(24))
        lateinit var jobScheduler: JobScheduler
        lateinit var jobInfo: JobInfo

    }
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

    //beállítások betöltése
        ModelPreferencesManager.with(this)
        try
        {
            val saveFile = ModelPreferencesManager.get<SaveFile>("KEY_SAVE")!!
            settings = saveFile.settings
        }
        catch (e: Exception) {
            for (x in settings.checkBoxArray.indices) {
                settings.checkBoxArray[x] = true
            }
        }

    //cikkek beszerzése
        val comm = Communicator()
        try
        {
            //cikkek betöltése fájlból
            var articles = mutableListOf<Article>()
            val saveFile = ModelPreferencesManager.get<SaveFile>("KEY_SAVE")!!
            val tArticles = saveFile.articles
            println("Talált cikkek: " +tArticles.size)
            println(tArticles[0]!!.title.rendered)
            val nArticles = comm.LoadArticles(10, forceLoad = true)
            for (x in nArticles.indices)
            {
                if (nArticles[x].id != tArticles[0]!!.id) articles.add(nArticles[x])
                else {break}
            }
            for (x in tArticles.indices)
            {
                articles.add(tArticles[x]!!)
            }
            gArticles=articles
        }
        catch (e: Exception)
        {
            println(e)
            //cikkek első betöltése
            var articles = mutableListOf<Article>()
            var atEnd = false
            var i = 0
            while (!atEnd)      //addig fusson amíg talál új cikket
            {
                val tArticles = comm.LoadArticles(settings.arNum, forceLoad = true,diff = i*settings.arNum)
                if (tArticles.count() <= 0) { atEnd = true }
                else {articles.addAll(tArticles)}
                i++
                println("i = $i, talát cikkek: ${tArticles.count()}")
            }
            gArticles=articles
        }

        val authors = comm.LoadAuthors()

    //értesítés deklarálás
        val cn = ComponentName(this,BackgroundScheduler::class.java)
        val builder: JobInfo.Builder = JobInfo.Builder(129,cn)
        builder.setPeriodic(30*60*1000)
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        builder.setPersisted(true)
        jobInfo = builder.build()
        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        if (settings.arNot)
        { jobScheduler.schedule(jobInfo) }
        else
        { jobScheduler.cancel(129) }

    //írónevek és id-k összefésülése
        for (x in gArticles.indices)
            for (y in authors.indices)
                {
                    if (gArticles[x].author == authors[y].id)
                            gArticles[x].authorName = authors[y].name
                }

    //ez a menüsávnak a definiálása:
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_filter, R.id.nav_archive,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()

        println("kiléptem")
        val aArticles = arrayOfNulls<Article>(gArticles.size)
        for (x in gArticles.indices) { aArticles[x] = gArticles[x] }
        println(aArticles.size)
        val saveFile = SaveFile(settings,aArticles)
        ModelPreferencesManager.put(saveFile,"KEY_SAVE")

        val sharedPreference =  getSharedPreferences("SAVE_PREF",Context.MODE_MULTI_PROCESS)
        val editor = sharedPreference.edit()
        editor.putInt("LAST_ARTICLE", gArticles[0].id)
        editor.apply()
    }

    override fun onStop() {
        super.onStop()

        println("kiléptem")
        val aArticles = arrayOfNulls<Article>(gArticles.size)
        for (x in gArticles.indices) { aArticles[x] = gArticles[x] }
        println(aArticles.size)
        val saveFile = SaveFile(settings,aArticles)
        ModelPreferencesManager.put(saveFile,"KEY_SAVE")

        val sharedPreference =  getSharedPreferences("SAVE_PREF",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("LAST_ARTICLE", gArticles[0].id)
        editor.apply()
    }

    override fun onPause() {
        super.onPause()

        val aArticles = arrayOfNulls<Article>(gArticles.size)
        for (x in gArticles.indices) { aArticles[x] = gArticles[x] }
        println(aArticles.size)
        val saveFile = SaveFile(settings,aArticles)
        ModelPreferencesManager.put(saveFile,"KEY_SAVE")

        val sharedPreference =  getSharedPreferences("SAVE_PREF",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("LAST_ARTICLE", gArticles[0].id)
        editor.apply()
    }
}

data class Settings(
    var allNot: Boolean = true,
    var arNot: Boolean = true,         //cikk értesítések on/off
    //egyéb értesítések ide...

    var checkBoxArray: BooleanArray,
    //egyéb szűrő pontok ide

    var arNum: Int = 20              //betöltendő cikkek száma
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Settings

        if (allNot != other.allNot) return false
        if (arNot != other.arNot) return false
        if (!checkBoxArray.contentEquals(other.checkBoxArray)) return false
        if (arNum != other.arNum) return false

        return true
    }

    override fun hashCode(): Int {
        var result = allNot.hashCode()
        result = 31 * result + arNot.hashCode()
        result = 31 * result + checkBoxArray.contentHashCode()
        result = 31 * result + arNum
        return result
    }
}

data class SaveFile(
    val settings: Settings,
    val articles: Array<Article?>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaveFile

        if (settings != other.settings) return false
        if (!articles.contentEquals(other.articles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = settings.hashCode()
        result = 31 * result + articles.contentHashCode()
        return result
    }
}

