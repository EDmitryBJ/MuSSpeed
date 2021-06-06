package com.nibble.musspeed

import android.os.Bundle
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView

class Map : AppCompatActivityWithMenuBar() {
    lateinit var webView: WebView
    lateinit var startInput: TextView
    lateinit var finishInput: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        initializeMenuBar()
        initializePathController()
    }
    private fun initializePathController(){
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
        startInput = findViewById(R.id.input_start)
        finishInput = findViewById(R.id.input_finish)
    }
}