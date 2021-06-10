package com.nibble.musspeed

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel
import kotlin.random.Random

class MusicService(context: Context) {
    private lateinit var title: TextView
    private lateinit var mSeekBar: SeekBar
    private val context = context
    private var tracksTypes = mutableListOf<String>(
        "Alternative",
        "Hip-hop.rap",
        "Indie",
        "Pop",
        "Rock"
    )

    fun SetTracksTypes(tracksTypes: MutableList<String>){
        this.tracksTypes = tracksTypes
    }

    fun Play(speed:Int, mediaPlayer: MediaPlayer, mainActivity: MainActivity){
        if(mediaPlayer.isPlaying)
            Stop(mediaPlayer, mainActivity)
            val musicType = tracksTypes[Random.nextInt(0, tracksTypes.count()-1)]
            mainActivity.Storage.reference.child("$musicType/").child("$musicType$speed/").listAll().addOnSuccessListener {
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
                    Play(speed, mediaPlayer, mainActivity)
            }
}

    fun Stop(mediaPlayer: MediaPlayer, mainActivity: MainActivity){
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                //mediaPlayer.release()
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

