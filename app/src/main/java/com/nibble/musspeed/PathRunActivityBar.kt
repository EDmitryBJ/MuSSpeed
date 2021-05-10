package com.nibble.musspeed

import android.os.Bundle
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.TextView

class PathRunActivityBar : AppCompatActivityWithMenuBar() {
    private lateinit var webView : WebView;
    private var progress : Int? = 0
    private var progressBar: ProgressBar? = null
    private var textViewProgressBar: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_run)
        initializeMenuBar()
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
        progressBar = findViewById(R.id.path_progress_bar);
        textViewProgressBar =  findViewById(R.id.text_view_path_progress_bar);
    }

    private fun postProgress(progress: Int) {
        val strProgress = "Вы прошли: $progress% пути"
        progressBar?.progress = progress
        if (progress == 0) {
            progressBar?.secondaryProgress = 0
        } else {
            progressBar?.secondaryProgress = progress + 5
        }
        textViewProgressBar?.text = strProgress
    }
}