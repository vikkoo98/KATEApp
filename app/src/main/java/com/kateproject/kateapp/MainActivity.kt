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

        for (x in settings.checkBoxArray.indices)
        {
            settings.checkBoxArray[x]=true
        }

    //cikkek első betöltése
        val comm = Communicator()
        val articles = comm.LoadArticles(settings.arNum, forceLoad = true)
        gArticles = articles
        val authors = comm.LoadAuthors()

    //értesítés deklarálás
        val cn = ComponentName(this,BackgroundScheduler::class.java)
        val builder: JobInfo.Builder = JobInfo.Builder(129,cn)
        builder.setPeriodic(60*60*1000)
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
