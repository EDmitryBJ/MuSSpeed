package com.nibble.musspeed

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class MainActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var btnToRun : ImageButton
    lateinit var btnToMap : ImageButton
    lateinit var btnToBody : ImageButton
    lateinit var btnToMus : ImageButton
    lateinit var btnToWorkout : ImageButton

    var mapFragment: Fragment? = null
    var map: GoogleMap? = null
    val polyline:MutableList<PolylineOptions> = mutableListOf()
    var currentLatitude:Double = 0.0
    var currentLongitude:Double = 0.0
    var targetLatitude:Double = 0.0
    var targetLongitude:Double = 0.0

    var trackRemainingTime = Int.MAX_VALUE

    var height: Int = 60
    var weight: Int = 180
    var sex:Boolean = true

    var currentPathStr: String = "Маршрут не выбран"
    var timeLeftStr: String = "Перейдите к выбору маршрута"
    var totalPath = 0
    var totalTime = 0
    var currentMusicSpeed: Int = 2

    var timeLeft = 0
    var currentPath = 0
    var firstTime:Int = 0
    var firstPath:Int = 0
    var deltaTime:Int = 0
    var deltaPath:Int = 0

    lateinit var currentModel: Model
    var SelectedMusicTypes = mutableListOf(
        "Alternative",
        "Hip-hop.rap",
        "Indie",
        "Pop",
        "Rock"
    )

    val storage = Firebase.storage


    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)

        super.onCreate(savedInstanceState)

        context = baseContext

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.profile_settings)

        currentModel = ProfileModel()
        currentModel.OpenWindow(this)
        initNavBar()
    }

    fun initNavBar(){
        btnToRun = findViewById(R.id.run_button)
        btnToRun.setOnClickListener {
            currentModel.CloseWindow(this)
            currentModel = ProgressModel()
            currentModel.OpenWindow(this)
        }

        btnToMap = findViewById(R.id.map)
        btnToMap.setOnClickListener {
                currentModel.CloseWindow(this)
                currentModel = MapModel()
                currentModel.OpenWindow(this)
        }

        btnToBody = findViewById(R.id.profile)
        btnToBody.setOnClickListener {
            currentModel.CloseWindow(this)
            currentModel = ProfileModel()
            currentModel.OpenWindow(this)
        }

        btnToMus = findViewById(R.id.music)
        btnToMus.setOnClickListener {
            currentModel.CloseWindow(this)
            currentModel = MusicModel()
            currentModel.OpenWindow(this)
        }

        btnToWorkout = findViewById(R.id.training)
        btnToWorkout.setOnClickListener {
            currentModel.CloseWindow(this)
            currentModel = TrainingModel()
            currentModel.OpenWindow(this)
        }
    }

    fun calculateNextTrackSpeed(){
        val deltaSpeed =  deltaPath/deltaTime
        val speed = totalPath/totalTime
        if (deltaSpeed>=speed){
            if(currentMusicSpeed - 1 < 0)
                currentMusicSpeed = 0
            else
                currentMusicSpeed--
        }
        else{
            if(currentMusicSpeed + 1 > 4)
                currentMusicSpeed = 4
            else
                currentMusicSpeed++
        }
}

    fun calculateDeltas(){
        deltaTime = timeLeft - firstTime
        deltaPath = currentPath - firstPath
    }
}
