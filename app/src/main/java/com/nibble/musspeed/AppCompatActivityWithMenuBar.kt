package com.nibble.musspeed

import android.content.Intent
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

open class AppCompatActivityWithMenuBar : AppCompatActivity() {
    protected lateinit var btnToRun : ImageButton
    protected lateinit var btnToMap : ImageButton
    protected lateinit var btnToBody : ImageButton
    protected lateinit var btnToMus : ImageButton
    protected lateinit var btnToWorkout : ImageButton

    protected inline fun <reified nextActivity> changeActivity(){
        val intent = Intent(this, nextActivity::class.java)
        startActivity(intent)
    }
    protected fun initializeMenuBar(){
        btnToRun = findViewById(R.id.to_path_run)
        btnToRun.setOnClickListener { changeActivity<PathRunActivityBar>() }
        btnToMap = findViewById(R.id.to_path_selector)
        btnToMap.setOnClickListener { changeActivity<PathSelectionActivityBar>() }
        btnToBody = findViewById(R.id.to_body_selector)
        btnToBody.setOnClickListener { changeActivity<BodySelectionActivityBar>() }
        btnToMus = findViewById(R.id.to_music_selector)
        btnToMus.setOnClickListener { changeActivity<MusicSelectionActivityBar>() }
        btnToWorkout = findViewById(R.id.to_workout_selector)
        btnToWorkout.setOnClickListener { changeActivity<WorkoutSelectionActivityBar>() }
    }
}