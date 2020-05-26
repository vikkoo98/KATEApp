package com.kateproject.kateapp

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
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
import android.widget.Toast
import kotlinx.android.synthetic.main.loading_screen.*

class MainActivity : AppCompatActivity() {

    companion object {
        var gArticles: List<Article> = emptyList()
        var settings = Settings(checkBoxArray = BooleanArray(24))
        lateinit var jobScheduler: JobScheduler //háttérfolyamathoz
        lateinit var jobInfo: JobInfo //háttérfolyamathoz
        var wasChecked = false //hogy ne töltse be a dolgokat mindig ha a főoldalra megyünk
        var canSave = true //ha valamiért nem találta meg az összes cikket, akkor inkább ne mentsen el...
    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var initializeTask: InitializeTask

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (!wasChecked) {
            setContentView(R.layout.loading_screen) //először loading screen legyen

            //setContentView(R.layout.activity_main)
            //val toolbar: Toolbar = findViewById(R.id.toolbar)
            //setSupportActionBar(toolbar)

            //értesítés deklarálás

            //háttérfolyamat
            textLoading.text = "   Betöltés...\n (az első betöltés hosszabb lehet)"

            initializeTask = @SuppressLint("StaticFieldLeak")
            object : InitializeTask(this) {
                override fun onPostExecute(result: Boolean?) {
                    super.onPostExecute(result)

                    //főoldal betöltése
                    setContentView(R.layout.activity_main)
                    val toolbar: Toolbar = findViewById(R.id.toolbar)
                    setSupportActionBar(toolbar)

                    //a cikkek számának checkolása
                    if (gArticles.count() <= 400)
                    {
                        canSave = false
                        Toast.makeText(applicationContext,"Hiba a cikkek betöltésével", Toast.LENGTH_SHORT).show()
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
            }
            initializeTask.execute()

            wasChecked = true
        }
        else
        {
            setContentView(R.layout.activity_main)
            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)

            //ez a menüsávnak a definiálása:
            val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            val navView: NavigationView = findViewById(R.id.nav_view)
            val navController = findNavController(R.id.nav_host_fragment)
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_filter, R.id.nav_archive,
                    R.id.nav_settings, R.id.nav_styles
                ), drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
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
    }

    override fun onStop() {
        super.onStop()

        println("kiléptem")
        val aArticles = arrayOfNulls<Article>(gArticles.size)
        for (x in gArticles.indices) { aArticles[x] = gArticles[x] }
        println(aArticles.size)
        val saveFile = SaveFile(settings,aArticles)
        ModelPreferencesManager.put(saveFile,"KEY_SAVE")
    }

    override fun onPause() {
        super.onPause()

        val saveFile: SaveFile
        if (canSave) {
            val aArticles = arrayOfNulls<Article>(gArticles.size)
            for (x in gArticles.indices) {
                aArticles[x] = gArticles[x]
            }
            println(aArticles.size)
            saveFile = SaveFile(settings, aArticles)
        }
        else
        {
            saveFile = SaveFile(settings, null)
        }
        ModelPreferencesManager.put(saveFile,"KEY_SAVE")
    }
}

data class Settings(
    var allNot: Boolean = true,
    var arNot: Boolean = true,         //cikk értesítések on/off
    //egyéb értesítések ide...

    var checkBoxArray: BooleanArray, //az összes checkbox egyben
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
    val articles: Array<Article?>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SaveFile

        if (settings != other.settings) return false
        if (articles != null) {
            if (other.articles?.let { articles.contentEquals(it) }!!) return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = settings.hashCode()
        result = 31 * result + articles!!.contentHashCode()
        return result
    }
}

