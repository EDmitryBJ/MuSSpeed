package com.nibble.musspeed

import android.os.Bundle

class BodySelectionActivityBar : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_selection)
        initializeMenuBar()
    }
}