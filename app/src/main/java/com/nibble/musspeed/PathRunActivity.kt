package com.nibble.musspeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView

class PathRunActivity : AppCompatActivity() {
    private lateinit var btnToRun : ImageButton
    private lateinit var btnToMap : ImageButton
    private lateinit var btnToBody : ImageButton
    private lateinit var btnToMus : ImageButton
    private lateinit var btnToWorkout : ImageButton
    private lateinit var webView : WebView;
    private var progress : Int? = 0
    private var progressBar: ProgressBar? = null
    private var textViewProgressBar: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_run)
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
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/index.html")
        progressBar = findViewById(R.id.path_progress_bar);
        textViewProgressBar =  findViewById(R.id.text_view_path_progress_bar);
    }

    private fun postProgress(progress: Int) {
        val strProgress = "Вы прошли: $progress% пути"
        progressBar?.progress = progress
        if (progress == 0) {
            progressBar?.secondaryProgress = 0
        } else {
            progressBar?.secondaryProgress = progress + 5
        }
        textViewProgressBar?.text = strProgress
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