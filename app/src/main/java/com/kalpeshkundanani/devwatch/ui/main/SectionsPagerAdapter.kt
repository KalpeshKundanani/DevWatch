package com.kalpeshkundanani.devwatch.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.kalpeshkundanani.devwatch.R
import com.kalpeshkundanani.devwatch.apps.AppListDialogFragment
import com.kalpeshkundanani.devwatch.battery.BatteryFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_battery,
    R.string.tab_text_apps
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> BatteryFragment.newInstance()
//        1 -> AppListDialogFragment.newInstance(3)
        else ->
            PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}