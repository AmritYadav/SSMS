package com.sts.ssms.custom.calendar.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.sts.ssms.custom.calendar.interfaces.NavigationListener
import com.sts.ssms.R
import com.sts.ssms.custom.calendar.helpers.DAY_CODE
import com.sts.ssms.custom.calendar.helpers.EventType
import com.sts.ssms.custom.calendar.helpers.Formatter
import com.sts.ssms.custom.calendar.helpers.MonthlyCalendarImpl
import com.sts.ssms.custom.calendar.interfaces.MonthlyCalendar
import com.sts.ssms.custom.calendar.models.DayMonthly
import com.sts.ssms.data.event.EventRepository
import kotlinx.android.synthetic.main.fragment_month.view.*
import kotlinx.android.synthetic.main.top_navigation.view.*
import org.joda.time.DateTime
import org.koin.android.ext.android.inject

class MonthFragment : Fragment(), MonthlyCalendar {
    private var mTextColor = 0
    private var mDayCode = ""
    private var mLastHash = 0L
    private var mCalendar: MonthlyCalendarImpl? = null

    var listener: NavigationListener? = null
    var eventType: EventType? = null

    private lateinit var mHolder: RelativeLayout

    private val eventRepository by inject<EventRepository>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_month, container, false)
        mHolder = view.month_calendar_holder
        mDayCode = arguments?.getString(DAY_CODE)!!

        eventRepository.setEventType(eventType)

        setupButtons()
        mCalendar = MonthlyCalendarImpl(this, requireActivity(), eventRepository)

        return view
    }

    override fun onResume() {
        super.onResume()

        mCalendar!!.apply {
            mTargetDate = Formatter.getDateTimeFromCode(mDayCode)
            getDays(false)    // pre fill the screen asap, even if without events
        }

        updateCalendar()
    }

    fun updateCalendar() {
        mCalendar?.updateMonthlyCalendar(Formatter.getDateTimeFromCode(mDayCode))
    }

    override fun updateMonthlyCalendar(
        context: Context, month: String, days: ArrayList<DayMonthly>, checkedEvents: Boolean,
        currTargetDate: DateTime
    ) {
        val newHash = month.hashCode() + days.hashCode().toLong()
        if ((mLastHash != 0L && !checkedEvents) || mLastHash == newHash) {
            return
        }

        mLastHash = newHash

        activity?.runOnUiThread {
            mHolder.top_value.apply {
                text = month
                contentDescription = text
                setTextColor(Color.BLACK)
            }
            updateDays(days)
        }
    }

    private fun setupButtons() {
        mTextColor = Color.BLACK

        mHolder.top_left_arrow.apply {
            background = null
            setOnClickListener {
                listener?.goLeft()
            }

            val pointerLeft = activity?.getDrawable(R.drawable.ic_chevron_left_vector)
            pointerLeft?.isAutoMirrored = true
            setImageDrawable(pointerLeft)
        }

        mHolder.top_right_arrow.apply {
            background = null
            setOnClickListener {
                listener?.goRight()
            }

            val pointerRight = activity?.getDrawable(R.drawable.ic_chevron_right_vector)
            pointerRight?.isAutoMirrored = true
            setImageDrawable(pointerRight)
        }

        mHolder.top_value.apply {
            setTextColor(Color.BLACK)
        }
    }

    private fun updateDays(days: ArrayList<DayMonthly>) {
        mHolder.month_view_wrapper.updateDays(days) {}
    }
}
