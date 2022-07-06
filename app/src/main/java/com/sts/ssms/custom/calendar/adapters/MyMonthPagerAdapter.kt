package com.sts.ssms.custom.calendar.adapters

import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.sts.ssms.custom.calendar.fragments.MonthFragment
import com.sts.ssms.custom.calendar.helpers.DAY_CODE
import com.sts.ssms.custom.calendar.helpers.EventType
import com.sts.ssms.custom.calendar.interfaces.NavigationListener

class MyMonthPagerAdapter(
    private val eventType: EventType?,
    fm: FragmentManager, private val mCodes: List<String>, private val mListener: NavigationListener
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragments = SparseArray<MonthFragment>()
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        val code = mCodes[position]
        bundle.putString(DAY_CODE, code)

        val fragment = MonthFragment()
        fragment.arguments = bundle
        fragment.listener = mListener
        fragment.eventType = eventType

        mFragments.put(position, fragment)
        return fragment
    }

    override fun getCount(): Int = mCodes.size

    fun updateCalendars(pos: Int) {
        for (i in -1..1) {
            mFragments[pos + i]?.updateCalendar()
        }
    }
}