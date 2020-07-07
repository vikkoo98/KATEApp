package com.kateproject.kateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_article.*
import org.sufficientlysecure.htmltextview.HtmlTextView
import android.os.Build
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

/*-------------------------------------------------------
---------------------------------------------------------
Mikor az olvasó megnyit egy cikket, ez az activity indul el,
ez jeleníti meg az adott cikket.

Hozzá tartozik: activity_article.xml
---------------------------------------------------------
---------------------------------------------------------*/

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (MainActivity.settings.theme)
        {
            ThemeType.BASE -> theme.applyStyle(R.style.ArticleTheme,true)
            ThemeType.DARK -> theme.applyStyle(R.style.ArticleDarkTheme,true)
            ThemeType.THEME3 -> theme.applyStyle(R.style.ArticleThirdTheme,true)
            ThemeType.NEPTUN -> theme.applyStyle(R.style.ArticleTheme,true)
        }

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val content = intent.getStringExtra("content")
        val date = intent.getStringExtra("date")
        setContentView(R.layout.activity_article)

        supportActionBar!!.title = "KÁTÉ"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            titleText.text = Html.fromHtml(title,Html.FROM_HTML_MODE_COMPACT).toString()
        }
        else
        {
            titleText.text = Html.fromHtml(title)
        }

        authortext.text=author
        if (date != null)
            dateText.text=date.replaceAfter('T',"").replace("-",". ").replace("T",". ")



        if (content !=  null)
        {
            val htmlTextView = findViewById<HtmlTextView>(R.id.contentText)
            htmlTextView.setHtml(content, HtmlHttpImageGetter(htmlTextView,null, true))
        }
    }
}
