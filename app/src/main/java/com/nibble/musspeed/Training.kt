package com.nibble.musspeed

import android.os.Bundle

class Training : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)
        initializeMenuBar()
    }
}