package com.kateproject.kateapp.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.core.text.set
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        //val textView: TextView = root.findViewById(R.id.text_settings)
        settingsViewModel.text.observe(this, Observer {

            //switchek
                switch1.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener {
                    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                        if (!isChecked)
                        {
                            switch2.isChecked=false
                            switch2.isEnabled=false
                            switch3.isChecked=false
                            switch3.isEnabled=false
                        }
                        else
                        {
                            switch2.isChecked=true
                            switch2.isEnabled=true
                            switch3.isChecked=true
                            switch3.isEnabled=true
                        }
                    }

                })

            articleNum.setText(20.toString())
            //a szövegeset a csúszkába
            articleNum.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    try {
                        if (articleNum.text.toString().toInt() > 50)
                            articleNum.setText(50.toString())
                        articleNumBar.progress = articleNum.text.toString().toInt()
                    }
                    catch(e: Exception)
                    {
                        articleNum.setText(0.toString())
                        articleNumBar.progress = 0
                    }
                }
                override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            })

            //a csúszkát a szövegbe
            articleNumBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    articleNum.setText(progress.toString())
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        })
        return root
    }
}