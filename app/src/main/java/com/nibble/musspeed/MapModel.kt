package com.nibble.musspeed

import android.webkit.WebView
import android.widget.TextView

class MapModel : Model {
    lateinit var webView: WebView
    lateinit var startInput: TextView
    lateinit var finishInput: TextView

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.path_selection)
        mainActivity.initNavBar()
        initializePathController(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }

    private fun initializePathController(mainActivity: MainActivity){
        webView = mainActivity.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
        startInput = mainActivity.findViewById(R.id.input_start)
        finishInput = mainActivity.findViewById(R.id.input_finish)
    }
}