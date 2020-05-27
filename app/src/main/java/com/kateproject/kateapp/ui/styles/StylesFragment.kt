package com.kateproject.kateapp.ui.styles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.R
import kotlinx.android.synthetic.main.fragment_style.*

class StylesFragment : Fragment() {

    private lateinit var stylesViewModel: StylesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
    stylesViewModel = ViewModelProviders.of(this).get(StylesViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_style, container, false)

    stylesViewModel.text.observe(this, Observer {

    })
    return root
}
}