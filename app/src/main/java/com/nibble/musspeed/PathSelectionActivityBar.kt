package com.nibble.musspeed

import android.os.Bundle

class PathSelectionActivityBar : AppCompatActivityWithMenuBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_path_selection)
        initializeMenuBar()
    }
}