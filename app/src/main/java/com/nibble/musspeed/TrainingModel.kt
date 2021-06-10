package com.nibble.musspeed

class TrainingModel : Model {

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.training_selection)
        mainActivity.InitNavBar()
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }
}