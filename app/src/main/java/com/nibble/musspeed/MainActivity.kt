package com.nibble.musspeed

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    lateinit var btnToRun : ImageButton
    lateinit var btnToMap : ImageButton
    lateinit var btnToBody : ImageButton
    lateinit var btnToMus : ImageButton
    lateinit var btnToWorkout : ImageButton
    var height: Int = 60
    var weight: Int = 180
    var currentPath: Double = 0.0
    var totalPath = 0.0
    var timeLeft: Double = 0.0
    var totalTime: Double = 0.0
    var currentMusicSpeed: Int = 0
    var late: Boolean = false
    lateinit var currentModel: Model


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.profile_settings)
        currentModel = ProfileModel()
        currentModel.OpenWindow(this)
        initNavBar()
    }

    fun initNavBar(){
        btnToRun = findViewById(R.id.run_button)
        btnToRun.setOnClickListener {
            currentModel.CloseWindow(this)
            setContentView(R.layout.progress)
            currentModel = ProgressModel()
            currentModel.OpenWindow(this)
        }

        btnToMap = findViewById(R.id.map)
        btnToMap.setOnClickListener {
            currentModel.CloseWindow(this)
            setContentView(R.layout.path_selection)
            currentModel = MapModel()
            currentModel.OpenWindow(this)
        }

        btnToBody = findViewById(R.id.profile)
        btnToBody.setOnClickListener {
            currentModel.CloseWindow(this)
            setContentView(R.layout.profile_settings)
            currentModel = ProfileModel()
            currentModel.OpenWindow(this)
        }

        btnToMus = findViewById(R.id.music)
        btnToMus.setOnClickListener {
            currentModel.CloseWindow(this)
            setContentView(R.layout.music_selection)
            currentModel = MusicModel()
            currentModel.OpenWindow(this)
        }

        btnToWorkout = findViewById(R.id.training)
        btnToWorkout.setOnClickListener {
            currentModel.CloseWindow(this)
            setContentView(R.layout.training_selection)
            currentModel = TrainingModel()
            currentModel.OpenWindow(this)
        }
    }

}