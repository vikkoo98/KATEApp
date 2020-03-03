package com.kateproject.kateapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.Communicator
import com.kateproject.kateapp.R
import com.kateproject.kateapp.MainActivity.Companion.settings
import kotlinx.android.synthetic.main.fragment_filter.*

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

            checkBoxArticle.isChecked=settings.articleFilter
            checkBoxInterview.isChecked=settings.interviewFilter

            checkBoxArticle.setOnCheckedChangeListener { _, _ ->
                settings.articleFilter=checkBoxArticle.isChecked
            }

            checkBoxInterview.setOnCheckedChangeListener { _, _ ->
                settings.interviewFilter=checkBoxInterview.isChecked
            }
        })
        return root
    }
}