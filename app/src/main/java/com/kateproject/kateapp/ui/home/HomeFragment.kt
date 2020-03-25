package com.kateproject.kateapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kateproject.kateapp.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.article_rec_view)
        homeViewModel.text.observe(this, Observer {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = RecyclerAdapter(it)

                //kereső a cikkekben
                val comm = Communicator()
                searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        println(query)
                        if (query != null)
                        {
                            val articles = comm.searchArticles(it,query)
                            recyclerView.adapter = RecyclerAdapter(articles)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        if (newText=="" || newText == null) { recyclerView.adapter = RecyclerAdapter(it) }
                        else
                        {
                            val articles = comm.searchArticles(it,newText)
                            recyclerView.adapter = RecyclerAdapter(articles)
                        }
                        return true
                    }
                })
            }

        })
        return root
    }

}