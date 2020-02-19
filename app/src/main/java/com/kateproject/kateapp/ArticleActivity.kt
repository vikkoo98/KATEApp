package com.kateproject.kateapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import kotlinx.android.synthetic.main.activity_article.*

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
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                contentText.text = Html.fromHtml(content, Html.FROM_HTML_MODE_COMPACT)
            } else {
                contentText.text = Html.fromHtml(content)
            }
        }
    }
}
