package com.kalpeshkundanani.devwatch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.kalpeshkundanani.devwatch.ui.main.SectionsPagerAdapter

/**
 * Main screen that will initialize all the tabs and fragments that user will be interacting with.
 *
 * @author: Kalpesh Kundanani.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTabs()
    }

    /**
     * This method will initialize the tab view and the view pager that can be seen on the main
     *  screen.
     */
    private fun initTabs() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}