package com.kateproject.kateapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.R

class FilterFragment : Fragment() {

    private lateinit var filterViewModel: FilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filterViewModel =
            ViewModelProviders.of(this).get(FilterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_filter, container, false)
        val textView: TextView = root.findViewById(R.id.text_filter)
        filterViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}