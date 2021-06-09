package com.nibble.musspeed

class MusicModel : Model {

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.music_selection)
        mainActivity.initNavBar()
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }
}