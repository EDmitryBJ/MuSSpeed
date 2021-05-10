package com.nibble.musspeed

import android.os.Bundle

class WorkoutSelectionActivityBar : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_selection)
        initializeMenuBar()
    }
}