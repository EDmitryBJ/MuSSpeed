package com.nibble.musspeed

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import android.widget.*


class PathRunActivityBar : AppCompatActivityWithMenuBar() {
    lateinit var urls : Array<String>
    private lateinit var btnPause : ImageButton
    private lateinit var btnNext : ImageButton
    private lateinit var webView : WebView
    private var progress : Int? = 0
    private lateinit var mSeekBar:SeekBar
    private lateinit var progressBar: ProgressBar
    private lateinit var textViewProgressBar: TextView
    private lateinit var title:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_run)
        initializeMenuBar()
        initializePathController()
        initializeButtons()

    }



    private fun initializeButtons(){
        val musicService = MusicService(this)
        btnPause = findViewById(R.id.pause)
        btnPause.setOnClickListener {
            musicService.stop()
            initializePlayerController(null)
            initializeTrackTitle("Waiting")
        }
        btnNext = findViewById(R.id.next)
        btnNext.setOnClickListener{
            val mp = musicService.play(0, MusicType.ALTERNATIVE)
            initializePlayerController(mp)
            initializeTrackTitle("Title coming soon")
        }
    }

    private fun initializePathController(){
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
        progressBar = findViewById(R.id.path_progress_bar);
        textViewProgressBar =  findViewById(R.id.text_view_path_progress_bar);
    }

    private fun initializePlayerController(mp:MediaPlayer?){
        if(mp!=null){
            mSeekBar = findViewById(R.id.music_seek_bar)
            mSeekBar.max = mp!!.duration/1000
            val mainHandler = Handler(Looper.getMainLooper())
            var p = 0
            mainHandler.post(object : Runnable {
                override fun run() {
                    if(mp.currentPosition != 0 || p == 0){
                        mSeekBar.progress = mp.currentPosition/1000;
                        mainHandler.postDelayed(this, 250)
                        p++
                    }
                }
            })
        }
        else
            mSeekBar.progress = 0
    }
    private fun initializeTrackTitle(name:String){
        title = findViewById(R.id.track_title)
        title.text = name
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
