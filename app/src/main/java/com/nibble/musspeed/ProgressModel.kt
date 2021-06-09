package com.nibble.musspeed

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView

class ProgressModel : Model{
    private lateinit var btnStop : ImageButton
    private lateinit var btnNext : ImageButton
    private lateinit var mSeekBar: SeekBar
    private lateinit var title:TextView
    private val mediaPlayer = MediaPlayer()
    private lateinit var musicService: MusicService

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.progress)
        mainActivity.initNavBar()
        initButtons(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {
        musicService.stop(mediaPlayer)
    }

    private fun initPlayerController(mainActivity: MainActivity){
        mSeekBar = mainActivity.findViewById(R.id.music_progress)

        try {
            if(!mediaPlayer.isPlaying){
                mSeekBar.progress = 0
                return
            }
        }catch (e:java.lang.Exception){
            mSeekBar.progress = 0
            return
        }
            mSeekBar.max = mediaPlayer.duration/1000
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post(object : Runnable {
                override fun run() {
                    try{
                        mSeekBar.progress = mediaPlayer.currentPosition/1000;
                        mainHandler.postDelayed(this, 300)
                    }
                    catch (e: Exception){
                    }
                }
            })
            mSeekBar.progress = 0
    }

    private fun initButtons(mainActivity: MainActivity){
        musicService = MusicService(mainActivity)
        btnStop = mainActivity.findViewById(R.id.stop)
        btnStop.setOnClickListener {
            musicService.stop(mediaPlayer)
            initPlayerController(mainActivity)
            setTrackTitle("Waiting", mainActivity)
        }

        btnNext = mainActivity.findViewById(R.id.next)
        btnNext.setOnClickListener{
            musicService.play(0, MusicType.ALTERNATIVE, mediaPlayer, mainActivity)
            initPlayerController(mainActivity)
            setTrackTitle("Title coming soon", mainActivity)
        }
    }

    private fun setTrackTitle(name:String, mainActivity: MainActivity){
        title = mainActivity.findViewById(R.id.track_title)
        title.text = name
    }
}