package com.nibble.musspeed

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.webianks.library.scroll_choice.ScrollChoice
import java.util.*

class MainActivity : AppCompatActivityWithMenuBar() {
    lateinit var myImageButton: ImageButton
    var height: MutableList<String> = mutableListOf()
    var mass: MutableList<String> = mutableListOf()
    lateinit var textView_height: TextView
    lateinit var textView_mass: TextView
    lateinit var scrollChoice_height: ScrollChoice
    lateinit var scrollChoice_mass: ScrollChoice
    lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeMenuBar()
        initializeSelectionMenu()
    }

    private fun initializeSelectionMenu(){
        viewPager2 = findViewById<ViewPager2>(R.id.viewPagerImageSlider)
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
        initViews_height()
        loadDatas_height()
        scrollChoice_height.addItems(height, 2)
        scrollChoice_height.setOnItemSelectedListener { scrollChoice_height, position_height, name_height ->
            textView_height.text = "Choice $name_height"
        }
        initViews_mass()
        loadDatas_mass()
        scrollChoice_mass.addItems(mass, 2)
        scrollChoice_mass.setOnItemSelectedListener { scrollChoice_mass, position_mass, name_mass ->
            textView_mass.text = "Choice $name_mass"
        }
    }

    private fun loadDatas_height() {
        for (i in 140..220) {
            height.add(i.toString())
        }
    }

    private fun initViews_height() {
        textView_height = findViewById<View>(R.id.txt_result_height) as TextView
        scrollChoice_height = findViewById<View>(R.id.scroll_choice_height) as ScrollChoice
    }

    private fun loadDatas_mass() {
        for (i in 40..200) {
            mass.add(i.toString())
        }
    }

    private fun initViews_mass() {
        textView_mass = findViewById<View>(R.id.txt_result_mass) as TextView
        scrollChoice_mass = findViewById<View>(R.id.scroll_choice_mass) as ScrollChoice
    }
}