package com.kateproject.kateapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kateproject.kateapp.R
import com.kateproject.kateapp.RecyclerAdapter
import com.kateproject.kateapp.recyclerAdapter

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
        val reciclerView: RecyclerView = root.findViewById(R.id.article_rec_view)
        homeViewModel.text.observe(this, Observer {
            reciclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                reciclerView.adapter = RecyclerAdapter(articles, context)
            }

        })
        return root
    }

}