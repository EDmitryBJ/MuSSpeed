package com.nibble.musspeed

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PowerManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.abs

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MapFragment(private val mainActivity: MainActivity) : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var init:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        if(!init){
            init = true
            val smf = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
            smf.getMapAsync { googleMap ->
                mainActivity.map = googleMap
                mainActivity.map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                initCurrentLocationChecker(mainActivity)
                if(mainActivity.currentModel::class.java == MapModel()::class.java)
                    mainActivity.map!!.setOnMapClickListener { latLng ->
                            requestDirections(latLng, mainActivity)
                }
            }
        }
        else{
            val smf = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
            smf.getMapAsync { googleMap ->
                mainActivity.map = googleMap
                mainActivity.map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                googleMap.clear()
                val mo = MarkerOptions()
                val latLng = LatLng(mainActivity.currentLatitude, mainActivity.currentLongitude)
                mo.position(latLng)
                mo.title("Вы")
                googleMap.addMarker(mo)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                if(mainActivity.polyline.count() != 0){
                    mainActivity.polyline.forEach{
                        googleMap.addPolyline(it)
                    }
                    mo.position(LatLng(mainActivity.targetLatitude, mainActivity.targetLongitude))
                    mo.title("Место назначения")
                    googleMap.addMarker(mo)
                }
                if(mainActivity.currentModel::class.java == MapModel()::class.java)
                    mainActivity.map!!.setOnMapClickListener { latLng ->
                            requestDirections(latLng, mainActivity)
                }
            }
        }
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return view
    }

//    override fun onResume() {
//        super.onResume()
//        initCurrentLocationChecker(mainActivity)
//    }

    private fun initCurrentLocationChecker(mainActivity: MainActivity){
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 10000
        mLocationRequest.fastestInterval = 1000
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val mLocationCallback: LocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    val gMap = mainActivity.map!!
                    if (location != null && (abs(mainActivity.currentLatitude - location.latitude) >= 0.005 || abs(mainActivity.currentLongitude - location.longitude) >= 0.005)) {
                            if(mainActivity.currentLongitude == 0.0 && mainActivity.currentLongitude == 0.0)
                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude,location.longitude), 15f))
                            mainActivity.currentLatitude = location.latitude
                            mainActivity.currentLongitude = location.longitude
                            val pm = mainActivity.getSystemService(Context.POWER_SERVICE) as PowerManager
                            if((mainActivity.targetLatitude != 0.0 || mainActivity.targetLongitude != 0.0)
                                &&((pm.isInteractive && (mainActivity.currentModel::class.java == MapModel()::class.java || mainActivity.currentModel::class.java == ProgressModel()::class.java))
                                || (mainActivity.trackRemainingTime <= mLocationRequest.interval && mainActivity.currentModel::class.java == ProgressModel()::class.java)))
                                requestDirections(LatLng(mainActivity.targetLatitude, mainActivity.targetLongitude),mainActivity)
                            else{
                                gMap.clear()
                                val mo = MarkerOptions()
                                val latLng = LatLng(location.latitude, location.longitude)
                                mo.position(latLng)
                                mo.title("Вы")
                                gMap.addMarker(mo)
                                if(mainActivity.polyline.count() != 0){
                                    mainActivity.polyline.forEach{
                                        gMap.addPolyline(it)
                                    }
                                    mo.position(LatLng(mainActivity.targetLatitude, mainActivity.targetLongitude))
                                    mo.title("Место назначения")
                                    gMap.addMarker(mo)
                                }
                            }
                    }
                }
            }
        }
        @Suppress("DEPRECATION")
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED )
            requestPermissions(Array(1){ Manifest.permission.ACCESS_FINE_LOCATION}, 1)
        @Suppress("DEPRECATION")
        if(ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            requestPermissions(Array(1){ Manifest.permission.ACCESS_COARSE_LOCATION}, 2)
        LocationServices.getFusedLocationProviderClient(mainActivity).requestLocationUpdates(mLocationRequest, mLocationCallback, null)
    }

    private fun requestDirections(latLng:LatLng, mainActivity: MainActivity){
        mainActivity.targetLatitude = latLng.latitude
        mainActivity.targetLongitude = latLng.longitude
        try {
            val sb = StringBuilder()
            val dataTransfer = Array(4){Any()}

            sb.append("https://maps.googleapis.com/maps/api/directions/json?")
            sb.append("origin=${mainActivity.currentLatitude},${mainActivity.currentLongitude}")
            sb.append("&destination=${latLng.latitude},${latLng.longitude}")
            sb.append("&mode=walking")
            sb.append("&key=${getString(R.string.google_api_key)}")

            val getDirectionsData = GetDirectionsData(mainActivity)
            dataTransfer[0] = mainActivity.map!!
            dataTransfer[1] = sb.toString()
            dataTransfer[2] = LatLng(mainActivity.currentLatitude, mainActivity.currentLongitude)
            dataTransfer[3] = LatLng(latLng.latitude, latLng.longitude)

            @Suppress("DEPRECATION")
            getDirectionsData.execute(dataTransfer)
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}