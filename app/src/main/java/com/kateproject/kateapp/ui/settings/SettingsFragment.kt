package com.kateproject.kateapp.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.Communicator
import com.kateproject.kateapp.R
import kotlinx.android.synthetic.main.fragment_settings.*
import com.kateproject.kateapp.MainActivity.Companion.settings
import com.kateproject.kateapp.MainActivity.Companion.jobInfo
import com.kateproject.kateapp.MainActivity.Companion.jobScheduler

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        settingsViewModel.text.observe(this, Observer {

            //létrehozáskor a jelenlegi állapot betöltése
            articleNum.setText(settings.arNum.toString())
            articleNumBar.progress=settings.arNum
            switch1.isChecked=settings.allNot
            switch2.isChecked=settings.arNot

            //switchek
                switch1.setOnCheckedChangeListener { _, isChecked ->
                    if (!isChecked) {
                        switch2.isChecked=false
                        switch2.isEnabled=false
                        switch3.isChecked=false
                        switch3.isEnabled=false
                    } else {
                        switch2.isChecked=true
                        switch2.isEnabled=true
                        switch3.isChecked=true
                        switch3.isEnabled=true
                    }
                }

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

            val comm = Communicator()
            saveButton.setOnClickListener {
                settings.allNot=switch1.isChecked
                settings.arNot=switch2.isChecked
                settings.arNum=articleNumBar.progress
                comm.LoadArticles(settings.arNum)
                println(settings.arNum)

                if (settings.arNot)
                {
                    jobScheduler.schedule(jobInfo)
                    Toast.makeText(context,"Értesítések bekapcsolva",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    jobScheduler.cancel(129)
                    Toast.makeText(context,"Értesítések kikapcsolva",Toast.LENGTH_SHORT).show()
                }
            }
        })
        return root
    }
}