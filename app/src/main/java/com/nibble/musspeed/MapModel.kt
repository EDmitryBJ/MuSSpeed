package com.nibble.musspeed

import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import java.util.*


class MapModel : Model {
    lateinit var textDistance: TextView
    lateinit var textDuration: TextView
    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.path_selection)
        mainActivity.initNavBar()
        initMap(mainActivity)
        initTextViews(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {
        closeMap(mainActivity)
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
        textDistance = mainActivity.findViewById(R.id.distance_text)
        textDuration = mainActivity.findViewById(R.id.duration_text)
    }

    fun setTexts(duration:String, distance:String){
        textDuration.text = duration
        textDistance.text = distance
    }
}