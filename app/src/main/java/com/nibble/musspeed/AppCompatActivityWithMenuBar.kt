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
        btnToRun = findViewById(R.id.run_button)
        btnToRun.setOnClickListener { changeActivity<Progress>() }
        btnToMap = findViewById(R.id.map)
        btnToMap.setOnClickListener { changeActivity<Map>() }
        btnToBody = findViewById(R.id.profile)
        btnToBody.setOnClickListener { changeActivity<MainActivity>() }
        btnToMus = findViewById(R.id.music)
        btnToMus.setOnClickListener { changeActivity<Music>() }
        btnToWorkout = findViewById(R.id.training)
        btnToWorkout.setOnClickListener { changeActivity<Training>() }
    }
}