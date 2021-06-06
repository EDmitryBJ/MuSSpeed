package com.nibble.musspeed

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView

class Progress : AppCompatActivityWithMenuBar(){
    private lateinit var btnStop : ImageButton
    private lateinit var btnNext : ImageButton
    private lateinit var mSeekBar: SeekBar
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var btnBack: ImageButton
    private lateinit var title:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        initializeMenuBar()
        initializeButtons()
    }

    private fun initializePlayerController(mp: MediaPlayer?){
        if(mp!=null){
            mSeekBar = findViewById(R.id.music_progress)
            mSeekBar.max = mp!!.duration/1000
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(object : Runnable {
                override fun run() {
                    try{
                        mSeekBar.progress = mp.currentPosition/1000;
                        mainHandler.postDelayed(this, 300)
                    }
                    catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
        }
        else
            mSeekBar.progress = 0
    }

    private fun initializeButtons(){
        val musicService = MusicService(this)
        btnBack = findViewById(R.id.back)
        btnBack.setOnClickListener{ changeActivity<Training>()}
        btnStop = findViewById(R.id.stop)
        btnStop.setOnClickListener {
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

    private fun initializeTrackTitle(name:String){
        title = findViewById(R.id.track_title)
        title.text = name
    }
}