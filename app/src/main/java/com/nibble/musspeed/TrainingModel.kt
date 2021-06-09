package com.nibble.musspeed

import android.os.Bundle

class TrainingModel : Model {

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.training_selection)
        mainActivity.initNavBar()
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }
}