package com.nibble.musspeed

import android.media.MediaPlayer
import android.widget.ImageButton
import android.widget.TextView

class ProgressModel : Model{
    private lateinit var btnStop : ImageButton
    private lateinit var btnNext : ImageButton
    private val mediaPlayer = MediaPlayer()
    private lateinit var musicService: MusicService
    lateinit var textDistance:TextView
    lateinit var textDuration:TextView

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.progress)
        mainActivity.initNavBar()
        initButtons(mainActivity)
        initMap(mainActivity)
        initTextViews(mainActivity)
        setTexts(mainActivity.timeLeftStr, mainActivity.currentPathStr)
    }

    override fun CloseWindow(mainActivity: MainActivity) {
        musicService.Stop(mediaPlayer, mainActivity)
        closeMap(mainActivity)
    }

    private fun initButtons(mainActivity: MainActivity){
        musicService = MusicService(mainActivity)
        btnStop = mainActivity.findViewById(R.id.stop)
        btnStop.setOnClickListener {
            musicService.Stop(mediaPlayer, mainActivity)
        }

        btnNext = mainActivity.findViewById(R.id.next)
        btnNext.setOnClickListener{
            musicService.Play( mediaPlayer, mainActivity)
        }
    }

    private fun initMap(mainActivity: MainActivity){
        if(mainActivity.mapFragment == null)
            mainActivity.mapFragment = MapFragment(mainActivity)
        mainActivity
            .supportFragmentManager
            .beginTransaction()
            .add(R.id.fake_layout, mainActivity.mapFragment!!, "map")
            .commit()
    }

    private fun closeMap(mainActivity: MainActivity){
        val f = mainActivity.supportFragmentManager.findFragmentByTag("map")
        if (f != null) mainActivity
            .supportFragmentManager
            .beginTransaction()
            .remove(f)
            .commit()
        mainActivity.supportFragmentManager.executePendingTransactions()
    }

    private fun initTextViews(mainActivity: MainActivity){
        textDistance= mainActivity.findViewById(R.id.remaining_distance)
        textDuration = mainActivity.findViewById(R.id.remaining_duration)
    }

    fun setTexts(duration:String, distance:String){
        textDuration.text = duration
        textDistance.text = distance
    }
}