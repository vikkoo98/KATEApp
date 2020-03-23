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

            checkBoxArticle.isChecked=settings.checkBoxArray[0]
            checkBoxLife.isChecked=settings.checkBoxArray[1]
            checkBoxEdu.isChecked=settings.checkBoxArray[2]
            checkBoxComp.isChecked=settings.checkBoxArray[3]
            checkBoxNews.isChecked=settings.checkBoxArray[4]
            checkBoxInterview.isChecked=settings.checkBoxArray[5]
            checkBoxKATV.isChecked=settings.checkBoxArray[6]
            checkBoxKepRiport.isChecked=settings.checkBoxArray[7]
            checkBoxCulture.isChecked=settings.checkBoxArray[8]
            checkBoxExhib.isChecked=settings.checkBoxArray[9]
            checkBoxBook.isChecked=settings.checkBoxArray[10]
            checkBoxCinema.isChecked=settings.checkBoxArray[11]
            checkBoxSport.isChecked=settings.checkBoxArray[12]
            checkBoxTheater.isChecked=settings.checkBoxArray[13]
            checkBoxMusic.isChecked=settings.checkBoxArray[14]
            checkBoxPalyazat.isChecked=settings.checkBoxArray[15]
            checkBoxProgram.isChecked=settings.checkBoxArray[16]
            checkBoxFest.isChecked=settings.checkBoxArray[17]
            checkBoxConcert.isChecked=settings.checkBoxArray[18]
            checkBoxParty.isChecked=settings.checkBoxArray[19]
            checkBoxRhely.isChecked=settings.checkBoxArray[20]
            checkBoxNoCom.isChecked=settings.checkBoxArray[21]
            checkBoxUncat.isChecked=settings.checkBoxArray[22]
            checkBoxVideo.isChecked=settings.checkBoxArray[23]

            checkBoxArticle.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[0]=isChecked }
            checkBoxLife.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[1]=isChecked }
            checkBoxEdu.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[2]=isChecked }
            checkBoxComp.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[3]=isChecked }
            checkBoxNews.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[4]=isChecked }
            checkBoxInterview.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[5]=isChecked }
            checkBoxKATV.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[6]=isChecked }
            checkBoxKepRiport.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[7]=isChecked }
            checkBoxCulture.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[8]=isChecked }
            checkBoxExhib.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[9]=isChecked }
            checkBoxBook.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[10]=isChecked }
            checkBoxCinema.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[11]=isChecked }
            checkBoxSport.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[12]=isChecked }
            checkBoxTheater.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[13]=isChecked }
            checkBoxMusic.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[14]=isChecked }
            checkBoxPalyazat.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[15]=isChecked }
            checkBoxProgram.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[16]=isChecked }
            checkBoxFest.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[17]=isChecked }
            checkBoxConcert.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[18]=isChecked }
            checkBoxParty.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[19]=isChecked }
            checkBoxRhely.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[20]=isChecked }
            checkBoxNoCom.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[21]=isChecked }
            checkBoxUncat.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[22]=isChecked }
            checkBoxVideo.setOnCheckedChangeListener{  _, isChecked -> settings.checkBoxArray[23]=isChecked }

        })
        return root
    }
}