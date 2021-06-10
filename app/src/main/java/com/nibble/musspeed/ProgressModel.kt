package com.nibble.musspeed

import android.media.MediaPlayer
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.TextView

class ProgressModel : Model{
    private lateinit var btnStop : ImageButton
    private lateinit var btnNext : ImageButton
    private lateinit var title:TextView
    private val mediaPlayer = MediaPlayer()
    private lateinit var musicService: MusicService
    lateinit var webView: WebView

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.progress)
        mainActivity.InitNavBar()
        initButtons(mainActivity)
        initMap(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {
        musicService.Stop(mediaPlayer, mainActivity)

    }

    private fun initButtons(mainActivity: MainActivity){
        musicService = MusicService(mainActivity)
        btnStop = mainActivity.findViewById(R.id.stop)
        btnStop.setOnClickListener {
            musicService.Stop(mediaPlayer, mainActivity)
            //SetTrackTitle("Waiting", mainActivity)
        }

        btnNext = mainActivity.findViewById(R.id.next)
        btnNext.setOnClickListener{
            musicService.Play(0, mediaPlayer, mainActivity)
            //SetTrackTitle("Title coming soon", mainActivity)
        }
    }

    private fun initMap(mainActivity: MainActivity){
        webView = mainActivity.findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
    }
}