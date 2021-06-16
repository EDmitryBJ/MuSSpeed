package com.nibble.musspeed

import android.widget.CheckBox

class MusicModel : Model {
    private lateinit var checkBoxAlternative:CheckBox
    private lateinit var checkBoxHipHop:CheckBox
    private lateinit var checkBoxIndie:CheckBox
    private lateinit var checkBoxPop:CheckBox
    private lateinit var checkBoxRock:CheckBox

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.music_selection)
        mainActivity.initNavBar()
        initCheckBoxes(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }

    private fun initCheckBoxes(mainActivity: MainActivity){
        checkBoxAlternative = mainActivity.findViewById(R.id.alternative_checkbox)
        checkBoxAlternative.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked && !mainActivity.SelectedMusicTypes.contains("Alternative"))
                    mainActivity.SelectedMusicTypes.add("Alternative")
                else if(!isChecked && mainActivity.SelectedMusicTypes.contains("Alternative"))
                    mainActivity.SelectedMusicTypes.remove("Alternative")
        }
        checkBoxHipHop = mainActivity.findViewById(R.id.hiphop_checkbox)
        checkBoxHipHop.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && !mainActivity.SelectedMusicTypes.contains("Hip-hop.rap"))
                mainActivity.SelectedMusicTypes.add("Hip-hop.rap")
            else if(!isChecked && mainActivity.SelectedMusicTypes.contains("Hip-hop.rap"))
                mainActivity.SelectedMusicTypes.remove("Hip-hop.rap")
        }
        checkBoxIndie = mainActivity.findViewById(R.id.indie_checkbox)
        checkBoxIndie.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && !mainActivity.SelectedMusicTypes.contains("Indie"))
                mainActivity.SelectedMusicTypes.add("Indie")
            else if(!isChecked && mainActivity.SelectedMusicTypes.contains("Indie"))
                mainActivity.SelectedMusicTypes.remove("Indie")
        }
        checkBoxPop = mainActivity.findViewById(R.id.pop_checkbox)
        checkBoxPop.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && !mainActivity.SelectedMusicTypes.contains("Pop"))
                mainActivity.SelectedMusicTypes.add("Pop")
            else if(!isChecked && mainActivity.SelectedMusicTypes.contains("Pop"))
                mainActivity.SelectedMusicTypes.remove("Pop")
        }
        checkBoxRock = mainActivity.findViewById(R.id.rock_checkbox)
        checkBoxRock.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked && !mainActivity.SelectedMusicTypes.contains("Rock"))
                mainActivity.SelectedMusicTypes.add("Rock")
            else if(!isChecked && mainActivity.SelectedMusicTypes.contains("Rock"))
                mainActivity.SelectedMusicTypes.remove("Rock")
        }
    }
}