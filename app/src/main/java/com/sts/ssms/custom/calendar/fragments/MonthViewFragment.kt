package com.sts.ssms.custom.calendar.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.sts.ssms.R
import com.sts.ssms.custom.calendar.adapters.MyMonthPagerAdapter
import com.sts.ssms.custom.calendar.base.BaseCalendarFragment
import com.sts.ssms.custom.calendar.common.MyViewPager
import com.sts.ssms.custom.calendar.helpers.DAY_CODE
import com.sts.ssms.custom.calendar.helpers.EventType
import com.sts.ssms.custom.calendar.helpers.Formatter
import com.sts.ssms.custom.calendar.interfaces.NavigationListener
import kotlinx.android.synthetic.main.fragment_months_holder.view.*

private const val PRE_FILLED_MONTHS = 251

class MonthViewFragment : BaseCalendarFragment(), NavigationListener {

    private var viewPager: MyViewPager? = null
    private var defaultMonthlyPage = 0
    private var todayDayCode = ""
    private var currentDayCode = ""
    var eventType: EventType? = null

    override fun goToToday() {
        currentDayCode = todayDayCode
        setupFragment()
    }

    override fun refreshEvents() {
        (viewPager?.adapter as? MyMonthPagerAdapter)?.updateCalendars(viewPager?.currentItem ?: 0)
    }

    override fun shouldGoToTodayBeVisible(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentDayCode = arguments?.getString(DAY_CODE) ?: ""
        todayDayCode = Formatter.getTodayCode()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_months_holder, container, false)
        view.background = ColorDrawable(Color.TRANSPARENT)
        viewPager = view.fragment_months_viewpager
        viewPager!!.id = (System.currentTimeMillis() % 100000).toInt()
        setupFragment()
        return view
    }

    private fun setupFragment() {
        val codes = getMonths(currentDayCode)
        val monthlyAdapter =
            MyMonthPagerAdapter(eventType, activity?.supportFragmentManager!!, codes, this)
        defaultMonthlyPage = codes.size / 2

        viewPager!!.apply {
            adapter = monthlyAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    currentDayCode = codes[position]
                }
            })
            currentItem = defaultMonthlyPage
        }
    }

    private fun getMonths(code: String): List<String> {
        val months = ArrayList<String>(PRE_FILLED_MONTHS)
        val today = Formatter.getDateTimeFromCode(code).withDayOfMonth(1)
        for (i in -PRE_FILLED_MONTHS / 2..PRE_FILLED_MONTHS / 2) {
            months.add(Formatter.getDayCodeFromDateTime(today.plusMonths(i)))
        }

        return months
    }

    override fun goLeft() {
        viewPager!!.currentItem = viewPager!!.currentItem - 1
    }

    override fun goRight() {
        viewPager!!.currentItem = viewPager!!.currentItem + 1
    }
}