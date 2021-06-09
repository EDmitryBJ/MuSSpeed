package com.nibble.musspeed

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.webianks.library.scroll_choice.ScrollChoice
import java.util.ArrayList

class ProfileModel : Model {
    var height: MutableList<String> = mutableListOf()
    var weight: MutableList<String> = mutableListOf()
    lateinit var textViewHeight: TextView
    lateinit var textViewWeight: TextView
    lateinit var scrollChoiceHeight: ScrollChoice
    lateinit var scrollChoiceWeight: ScrollChoice
    lateinit var viewPager2: ViewPager2

    override fun OpenWindow(mainActivity: MainActivity) {
        mainActivity.setContentView(R.layout.profile_settings)
        mainActivity.initNavBar()
        initializeSelectionMenu(mainActivity)
    }

    override fun CloseWindow(mainActivity: MainActivity) {

    }

    private fun initializeSelectionMenu(mainActivity:MainActivity){
        viewPager2 = mainActivity.findViewById(R.id.viewPagerImageSlider)
        val sliderItems: MutableList<SliderItem> = ArrayList()
        sliderItems.add(SliderItem(R.drawable.man))
        sliderItems.add(SliderItem(R.drawable.woman))
        viewPager2.adapter = SliderAdapter(sliderItems, viewPager2)
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.offscreenPageLimit = 3
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(0))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        viewPager2.setPageTransformer(compositePageTransformer)
        textViewHeight = mainActivity.findViewById<View>(R.id.txt_result_height) as TextView
        scrollChoiceHeight = mainActivity.findViewById<View>(R.id.scroll_choice_height) as ScrollChoice
        for (i in 140..220) {
            height.add(i.toString())
        }
        scrollChoiceHeight.addItems(height, 2)
        scrollChoiceHeight.setOnItemSelectedListener { scrollChoice_height, position_height, name_height ->
            textViewHeight.text = "Choice $name_height"
        }
        textViewWeight = mainActivity.findViewById<View>(R.id.txt_result_mass) as TextView
        scrollChoiceWeight = mainActivity.findViewById<View>(R.id.scroll_choice_mass) as ScrollChoice
        for (i in 40..200) {
            weight.add(i.toString())
        }
        scrollChoiceWeight.addItems(weight, 2)
        scrollChoiceWeight.setOnItemSelectedListener { scrollChoice_mass, position_mass, name_mass ->
            textViewWeight.text = "Choice $name_mass"
        }
    }
}