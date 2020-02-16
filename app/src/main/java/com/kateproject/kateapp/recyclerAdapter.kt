package com.kateproject.kateapp


import android.content.Context
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
        holder.view.textViewTitle.text = article.title.rendered
        holder.view.textViewExc.text = article.excerpt.rendered
        holder.view.textViewAut.text = article.authorName
        holder.view.textViewDate.text = article.date
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
