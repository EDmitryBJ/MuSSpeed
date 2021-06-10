package com.nibble.musspeed


import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView


class MapModel : Model {
    lateinit var webView: WebView

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.path_selection)
        mainActivity.InitNavBar()
        initializePathController(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }

    private fun initializePathController(mainActivity: MainActivity){
        webView = mainActivity.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
    }
}