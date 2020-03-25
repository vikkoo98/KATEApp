package com.kateproject.kateapp.ui.archive
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.kateproject.kateapp.R
import kotlinx.android.synthetic.main.fragment_archive.*

class ArchiveFragment : Fragment() {

    private lateinit var archiveViewModel: ArchiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        archiveViewModel =
            ViewModelProviders.of(this).get(ArchiveViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_archive, container, false)
        archiveViewModel.text.observe(this, Observer {
            mWebview.settings.javaScriptEnabled = true
            mWebview.loadUrl("https://issuu.com/bme-gepeszmernoki-kar")


        })
        return root
    }
}