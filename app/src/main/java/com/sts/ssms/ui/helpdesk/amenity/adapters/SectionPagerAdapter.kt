package com.sts.ssms.ui.helpdesk.amenity.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.amenity.AllAmenityFragment
import com.sts.ssms.ui.helpdesk.amenity.AmenityAllRequestFragment
import com.sts.ssms.ui.helpdesk.amenity.AmenityMyRequestFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_all_request,
    R.string.tab_my_request,
    R.string.tab_amenities
)

class SectionPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> AmenityAllRequestFragment()
        1 -> AmenityMyRequestFragment()
        else -> AllAmenityFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}