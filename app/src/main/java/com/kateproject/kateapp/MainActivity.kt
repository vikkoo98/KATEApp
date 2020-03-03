package com.kateproject.kateapp

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
        var settings = Settings()

    }
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val comm = Communicator()
        val articles = comm.LoadArticles(settings.arNum, forceLoad = true)
        gArticles = articles
        val authors = comm.LoadAuthors()

        //írónevek és id-k összefésülése
        for (x in 0 until gArticles.count())
            for (y in 0 until authors.count())
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
    var arNot: Boolean = true,         //cikk értesítések on/off
    //egyéb értesítések ide...

    var articleFilter: Boolean = true,  //cikkek szűrője
    var interviewFilter: Boolean = true,//interjú szűrő
    var adFilter: Boolean = true,       //hirdetés szűrő
    //egyéb szűrő pontok ide

    var arNum: Int = 20              //betöltendő cikkek száma
)
