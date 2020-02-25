package com.kateproject.kateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_article.*
import org.sufficientlysecure.htmltextview.HtmlTextView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter


class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val content = intent.getStringExtra("content")
        val date = intent.getStringExtra("date")
        setContentView(R.layout.activity_article)

        titleText.text=title
        authortext.text=author
        //contentText.text=content
        dateText.text=date



        if (content !=  null)
        {/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentText.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                contentText.text = Html.fromHtml(content)
            }*/

            val htmlTextView = findViewById<HtmlTextView>(R.id.contentText)
            htmlTextView.setHtml(content, HtmlHttpImageGetter(htmlTextView))
        }
    }
}
