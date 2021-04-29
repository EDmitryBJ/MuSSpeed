package com.nibble.musspeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton

class BodySelectionActivity : AppCompatActivity() {
    private lateinit var btnToRun : ImageButton
    private lateinit var btnToMap : ImageButton
    private lateinit var btnToBody : ImageButton
    private lateinit var btnToMus : ImageButton
    private lateinit var btnToWorkout : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_selection)
        btnToRun = findViewById(R.id.to_path_run)
        btnToRun.setOnClickListener { toRun() }
        btnToMap = findViewById(R.id.to_path_selector)
        btnToMap.setOnClickListener { toMap() }
        btnToBody = findViewById(R.id.to_body_selector)
        btnToBody.setOnClickListener { toBody() }
        btnToMus = findViewById(R.id.to_music_selector)
        btnToMus.setOnClickListener { toMus() }
        btnToWorkout = findViewById(R.id.to_workout_selector)
        btnToWorkout.setOnClickListener { toWorkout() }
    }

    private fun toRun(){
        val intent = Intent(this, PathRunActivity::class.java)
        startActivity(intent)
    }
    private fun toMap(){
        val intent = Intent(this, PathSelectionActivity::class.java)
        startActivity(intent)
    }
    private fun toBody(){
        val intent = Intent(this, BodySelectionActivity::class.java)
        startActivity(intent)
    }
    private fun toMus(){
        val intent = Intent(this, MusicSelectionActivity::class.java)
        startActivity(intent)
    }
    private fun toWorkout(){
        val intent = Intent(this, WorkoutSelectionActivity::class.java)
        startActivity(intent)
    }
}