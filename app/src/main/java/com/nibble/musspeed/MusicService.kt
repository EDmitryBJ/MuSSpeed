package com.nibble.musspeed

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import java.io.IOException
import java.lang.Exception
import kotlin.random.Random

class MusicService(context: Context) {
    private lateinit var title: TextView
    private lateinit var mSeekBar: SeekBar
    private var tracksTypes = mutableListOf<String>(
        "Alternative",
        "Hip-hop.rap",
        "Indie",
        "Pop",
        "Rock"
    )

    fun Play(mediaPlayer: MediaPlayer, mainActivity: MainActivity){
        if(mediaPlayer.isPlaying)
            Stop(mediaPlayer, mainActivity)
        mainActivity.firstTime = mainActivity.timeLeft
        mainActivity.firstPath = mainActivity.currentPath
        val musicType = tracksTypes[Random.nextInt(0, tracksTypes.count()-1)]
        mediaPlayer.setOnCompletionListener {
            mainActivity.calculateDeltas()
            mainActivity.calculateNextTrackSpeed()
            Stop(mediaPlayer, mainActivity)
            Play(mediaPlayer,mainActivity)
        }
        mainActivity.storage.reference.child("$musicType/").child("$musicType${mainActivity.currentMusicSpeed}/").listAll().addOnSuccessListener {
                if(it.items.count()-1>=0){
                    val trackNum =Random.nextInt(0, it.items.count()-1)
                    setTrackTitle(it?.items!![trackNum].name.substring(10), mainActivity)
                    it?.items!![trackNum].downloadUrl.addOnSuccessListener { uri ->
                        mediaPlayer.setDataSource(uri.toString())
                        mediaPlayer.setOnPreparedListener {
                            mediaPlayer.start()
                            initPlayerController(mainActivity, mediaPlayer)
                        }
                        mediaPlayer.prepareAsync()
                    }
                }
                else
                    Play(mediaPlayer, mainActivity)
            }
}

    fun Stop(mediaPlayer: MediaPlayer, mainActivity: MainActivity){
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                initPlayerController(mainActivity, mediaPlayer)
                setTrackTitle("Маршрут приостановлен", mainActivity)
            }
        }
        catch (e:IOException){
            e.printStackTrace()
        }
    }

    private fun initPlayerController(mainActivity: MainActivity, mediaPlayer: MediaPlayer){
        mSeekBar = mainActivity.findViewById(R.id.music_progress)
        if(!mediaPlayer.isPlaying){
            mSeekBar.progress = 0
            mainActivity.trackRemainingTime = Int.MAX_VALUE
            return
        }
        mSeekBar.max = mediaPlayer.duration/1000
        val musicProgressHandler = Handler(Looper.getMainLooper())
        musicProgressHandler.post(object : Runnable {
            override fun run() {
                try{
                    mSeekBar.progress = mediaPlayer.currentPosition/1000
                    musicProgressHandler.postDelayed(this, 300)
                    mainActivity.trackRemainingTime = mediaPlayer.duration - mediaPlayer.currentPosition
                }
                catch (e: Exception){ }
            }
        })
        mSeekBar.progress = 0
    }

    private fun setTrackTitle(name:String, mainActivity: MainActivity){
        title = mainActivity.findViewById(R.id.track_title)
        title.text = name
    }
}

