package com.sts.ssms.custom.calendar.interfaces

import android.content.Context
import com.sts.ssms.custom.calendar.models.DayMonthly
import org.joda.time.DateTime

interface MonthlyCalendar {
    fun updateMonthlyCalendar(context: Context, month: String, days: ArrayList<DayMonthly>, checkedEvents: Boolean, currTargetDate: DateTime)
}
