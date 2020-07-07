package com.kateproject.kateapp.ui.styles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.MainActivity.Companion.settings
import com.kateproject.kateapp.R
import com.kateproject.kateapp.ThemeType
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

        when (settings.theme) //a témát itt állítja be
        {
            ThemeType.BASE -> baseStyle.isChecked = true
            ThemeType.DARK -> darkStyle.isChecked = true
            ThemeType.THEME3 -> thirdStyle.isChecked = true
            ThemeType.NEPTUN -> neptunStyle.isChecked = true
        }

        //ami eztán van az nem elegáns, de itt még kezelhető...
        baseStyle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                darkStyle.isChecked = false
                thirdStyle.isChecked = false
                neptunStyle.isChecked = false
            }
        }
        darkStyle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                baseStyle.isChecked = false
                thirdStyle.isChecked = false
                neptunStyle.isChecked = false
            }
        }
        thirdStyle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                darkStyle.isChecked = false
                baseStyle.isChecked = false
                neptunStyle.isChecked = false
            }
        }
        neptunStyle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                darkStyle.isChecked = false
                thirdStyle.isChecked = false
                baseStyle.isChecked = false
            }
        }

        buttonApply.setOnClickListener {
            when {
                baseStyle.isChecked -> settings.theme = ThemeType.BASE
                darkStyle.isChecked -> settings.theme = ThemeType.DARK
                thirdStyle.isChecked -> settings.theme = ThemeType.THEME3
                neptunStyle.isChecked -> settings.theme = ThemeType.NEPTUN
            }

            when (settings.theme) //a témát itt állítja be
            {
                ThemeType.BASE -> context?.theme?.applyStyle(R.style.AppBaseTheme_NoActionBar,true)
                ThemeType.DARK -> context?.theme?.applyStyle(R.style.AppDarkModeTheme_NoActionBar,true)
                ThemeType.THEME3 -> context?.theme?.applyStyle(R.style.AppThirdModeTheme_NoActionBar,true)
                ThemeType.NEPTUN -> context?.theme?.applyStyle(R.style.AppBaseTheme_NoActionBar,true)
            }
            activity?.recreate()
        }
    })
    return root
}
}