package com.nibble.musspeed

import android.os.Bundle

class Music : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initializeMenuBar()

    }
}