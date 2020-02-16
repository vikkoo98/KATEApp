package com.kateproject.kateapp

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout1.view.*

class RecyclerAdapter(private val articles: List<Article>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_layout1, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.view.textViewTitle.text = Html.fromHtml(article.title.rendered, Html.FROM_HTML_MODE_COMPACT)
            holder.view.textViewExc.text = Html.fromHtml(article.excerpt.rendered, Html.FROM_HTML_MODE_COMPACT)
            holder.view.textViewAut.text = Html.fromHtml(article.authorName, Html.FROM_HTML_MODE_COMPACT)
            holder.view.textViewDate.text = Html.fromHtml(article.date, Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.view.textViewTitle.text=Html.fromHtml(article.title.rendered)
            holder.view.textViewExc.text = Html.fromHtml(article.excerpt.rendered)
            holder.view.textViewAut.text = Html.fromHtml(article.authorName)
            holder.view.textViewDate.text = Html.fromHtml(article.date)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
