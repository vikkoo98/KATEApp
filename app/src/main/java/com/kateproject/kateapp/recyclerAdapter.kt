package com.kateproject.kateapp

import android.content.Intent
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout1.view.*

/*-------------------------------------------------------
---------------------------------------------------------
A főoldalon a cikkek listájának kezelését végzi ez a class,
megjeleníti őket egymás után, kattintásra megnyitja az adott
cikk AricleActivity-jét.
Hozzá tartozik: row_layout1.xml
---------------------------------------------------------
---------------------------------------------------------*/

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
            holder.view.textViewDate.text = Html.fromHtml(article.date.replaceAfter('T',"").replace("-",". ").replace("T",". "), Html.FROM_HTML_MODE_COMPACT)
        } else {
            holder.view.textViewTitle.text = Html.fromHtml(article.title.rendered)
            holder.view.textViewExc.text = Html.fromHtml(article.excerpt.rendered)
            holder.view.textViewAut.text = Html.fromHtml(article.authorName)
            holder.view.textViewDate.text = Html.fromHtml(article.date.replaceAfter('T',"").replace("-",". ").replace("T",". "))
        }

        //hogy új-e a cikk attól függően ott van a new felirat
        if (article.new) {
            holder.view.newImageView.visibility = View.VISIBLE
            holder.view.newImageView.setImageDrawable(holder.view.context.resources.getDrawable(R.drawable.ic_fiber_new_white))
        }
        else {
            holder.view.newImageView.visibility = View.GONE
        }

        //holder.view.textViewExc.text = article.tipus.toString() //ez a sor arra van hogy lássam a típusokat kiírva ha kell...

        //az interju típus színe
        if (article.tipus.contains(ArticleType.INTERJU)) {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorInterju)
        }
        //az élet típusú cikkek színe
        else if (article.tipus.contains(ArticleType.ELET)
            || article.tipus.contains(ArticleType.OKTATAS)
            || article.tipus.contains(ArticleType.VERSENY)) {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorElet)
        }
        //a kultúra típus színe
        else if (article.tipus.contains(ArticleType.KULTURA)
            || article.tipus.contains(ArticleType.KIALLITAS)
            || article.tipus.contains(ArticleType.KONYV)
            || article.tipus.contains(ArticleType.MOZI)
            || article.tipus.contains(ArticleType.SPORT)
            || article.tipus.contains(ArticleType.SZINHAZ)
            || article.tipus.contains(ArticleType.ZENE)) {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorKultura)
        }
        //a gpogram típus színe
        else if (article.tipus.contains(ArticleType.PROGRAM)
            || article.tipus.contains(ArticleType.FESZTIVAL)
            || article.tipus.contains(ArticleType.KONCERT)
            || article.tipus.contains(ArticleType.PARTY)) {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorProgram)
        }
        //a cikk és Uncategorized színe, mert mindig elfelejtik beállítani...
        else if (article.tipus.contains(ArticleType.CIKK)
            || article.tipus.contains(ArticleType.UNCATEGORIZED)) {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorHir)
        }
        //ha semmi se akkor default...
        else {
            holder.view.backgroundColor.backgroundTintList = holder.view.context.resources.getColorStateList(R.color.ColorDefault)
        }

        holder.view.setOnClickListener {
            val intent = Intent(holder.view.context, ArticleActivity::class.java)
            intent.putExtra("title", article.title.rendered)
            intent.putExtra("author", article.authorName)
            intent.putExtra("content", article.content.rendered)
            intent.putExtra("date", article.date)
            articles[position].new = false
            holder.view.context.startActivity(intent)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
