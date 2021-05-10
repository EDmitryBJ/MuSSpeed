package com.nibble.musspeed

import android.os.Bundle

class MusicSelectionActivityBar : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_selection)
        initializeMenuBar()
    }
}