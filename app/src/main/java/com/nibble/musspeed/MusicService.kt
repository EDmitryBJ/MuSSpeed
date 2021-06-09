package com.nibble.musspeed

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.nio.channels.Channels
import java.nio.channels.ReadableByteChannel

enum class MusicType (val value : Int) {
    ALTERNATIVE(0),
    HIPHOP_OR_RAP(1),
    ROCK(2),
    POP(3),
    INDIE(4)
}

class MusicService(context: Context) {
    private val storage = Firebase.storage
    private val context = context
    private val tracks =
        //type
        mutableListOf(
        //speed
        mutableListOf(
            //path and currentIdx
            Pair(mutableListOf(
                R.raw.amy_shark_i_said_hi,
                R.raw.fanfarlo_luna,
                R.raw.my_dove_my_lamb,
                R.raw.the_raconteurs_together,
                R.raw.the_unlikely_candidates_oh_my_dear_lord,
                R.raw.tindersticks_the_amputees,
                R.raw.tom_walker_leave_a_light_on,
                R.raw.tones_and_i_jimmy,
                R.raw.zayde_wolf_born_ready),0)
        )
    )
    fun play(speed:Int, trackType:MusicType, mediaPlayer: MediaPlayer, mainActivity: MainActivity){
        try {
            val alternative0 = storage.reference.child("/Alternative0")
            val track = alternative0.child("/amy_shark_i_said_hi.mp3")
            val url = track.downloadUrl.addOnSuccessListener {
                mediaPlayer.setDataSource(it.toString())
                mediaPlayer.setOnPreparedListener {
                    mediaPlayer.start()
                    Toast.makeText(mainActivity,"Audio started playing", Toast.LENGTH_SHORT).show()
                }
                mediaPlayer.prepareAsync()
            }
        }catch (e:Exception){
            e.printStackTrace()
            Toast.makeText(mainActivity,"Error", Toast.LENGTH_SHORT).show()
        }
//        var name =
//    try {
//        if(tracks[trackType.value][speed]==null)
//        {
//            Toast.makeText(context, "Sorry", Toast.LENGTH_SHORT).show()
//        }
//        val trackIdx = tracks[trackType.value][speed].first[tracks[trackType.value][speed].second]
//        if(mediaPlayer!=null){
//            mediaPlayer!!.stop()
//            mediaPlayer!!.reset()
//            mediaPlayer!!.release()
//        }
//        mediaPlayer = MediaPlayer.create(context, trackIdx)
//        mediaPlayer.setDataSource(context, trackIdx)
//        mediaPlayer!!.setOnPreparedListener {
//            mediaPlayer!!.start()
//            Toast.makeText(context, "Audio started playing.", Toast.LENGTH_SHORT).show()
//            if(tracks[trackType.value][speed].second + 1 < tracks[trackType.value][speed].first.size)
//                tracks[trackType.value][speed] = Pair(tracks[trackType.value][speed].first, tracks[trackType.value][speed].second + 1)
//            else
//                tracks[trackType.value][speed] = Pair(tracks[trackType.value][speed].first, 0)
//        }
//    }catch (e:IOException){
//        e.printStackTrace()
//    }
}

    fun stop(mediaPlayer: MediaPlayer){
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        }
        catch (e:IOException){
            e.printStackTrace()
        }
    }

}

