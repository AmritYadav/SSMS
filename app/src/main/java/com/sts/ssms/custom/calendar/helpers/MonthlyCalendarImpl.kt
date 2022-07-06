package com.sts.ssms.custom.calendar.helpers

import android.content.Context
import com.sts.ssms.custom.calendar.interfaces.MonthlyCalendar
import com.sts.ssms.custom.calendar.models.DayMonthly
import com.sts.ssms.custom.calendar.models.Event
import com.sts.ssms.data.event.EventRepository
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.ArrayList

private const val DAYS_CNT = 42
private const val YEAR_PATTERN = "YYYY"

class MonthlyCalendarImpl(
    private val callback: MonthlyCalendar,
    val context: Context,
    private val eventRepository: EventRepository
) {

    private val mToday: String = DateTime().toString(Formatter.DAY_CODE_PATTERN)
    private var mEvents: List<Event> = ArrayList()

    lateinit var mTargetDate: DateTime

    fun updateMonthlyCalendar(targetDate: DateTime) {
        mTargetDate = targetDate
//        val startTS = mTargetDate.minusDays(7).seconds()
//        val endTS = mTargetDate.plusDays(43).seconds()
        eventRepository.loadEvents { events ->
            events.forEach { it.updateIsPastEvent() }.apply { gotEvents(events) }
        }
    }

    fun getDays(markDaysWithEvents: Boolean) {
        val days = ArrayList<DayMonthly>(DAYS_CNT)
        val currMonthDays = mTargetDate.dayOfMonth().maximumValue
        val firstDayIndex = mTargetDate.withDayOfMonth(1).dayOfWeek
        val prevMonthDays = mTargetDate.minusMonths(1).dayOfMonth().maximumValue
        var isThisMonth = false
        var isToday: Boolean
        var value = prevMonthDays - firstDayIndex + 1
        var curDay = mTargetDate

        for (i in 0 until DAYS_CNT) {
            when {
                i < firstDayIndex -> {
                    isThisMonth = false
                    curDay = mTargetDate.withDayOfMonth(1).minusMonths(1)
                }
                i == firstDayIndex -> {
                    value = 1
                    isThisMonth = true
                    curDay = mTargetDate
                }
                value == currMonthDays + 1 -> {
                    value = 1
                    isThisMonth = false
                    curDay = mTargetDate.withDayOfMonth(1).plusMonths(1)
                }
            }

            isToday = isToday(curDay, value)

            val newDay = curDay.withDayOfMonth(value)
            val dayCode = Formatter.getDayCodeFromDateTime(newDay)
            val day = DayMonthly(
                value, isThisMonth, isToday, dayCode, newDay.weekOfWeekyear, ArrayList(), i
            )
            days.add(day)
            value++
        }

        if (markDaysWithEvents) {
            markDaysWithEvents(days)
        } else {
            callback.updateMonthlyCalendar(context, monthName, days, false, mTargetDate)
        }
    }

    // it works more often than not, don't touch
    private fun markDaysWithEvents(days: ArrayList<DayMonthly>) {
        val dayEvents = HashMap<String, ArrayList<Event>>()
        mEvents.forEach {
            val startDateTime = Formatter.getDateTimeFromTS(it.startTS)
            val endDateTime = Formatter.getDateTimeFromTS(it.endTS)
            val endCode = Formatter.getDayCodeFromDateTime(endDateTime)

            var currDay = startDateTime
            var dayCode = Formatter.getDayCodeFromDateTime(currDay)
            var currDayEvents = dayEvents[dayCode] ?: ArrayList()
            currDayEvents.add(it)
            dayEvents[dayCode] = currDayEvents

            while (Formatter.getDayCodeFromDateTime(currDay) != endCode) {
                currDay = currDay.plusDays(1)
                dayCode = Formatter.getDayCodeFromDateTime(currDay)
                currDayEvents = dayEvents[dayCode] ?: ArrayList()
                currDayEvents.add(it)
                dayEvents[dayCode] = currDayEvents
            }
        }

        days.filter { dayEvents.keys.contains(it.code) }.forEach {
            it.dayEvents = dayEvents[it.code]!!
        }
        callback.updateMonthlyCalendar(context, monthName, days, true, mTargetDate)
    }

    private fun isToday(targetDate: DateTime, curDayInMonth: Int): Boolean {
        val targetMonthDays = targetDate.dayOfMonth().maximumValue
        return targetDate.withDayOfMonth(curDayInMonth.coerceAtMost(targetMonthDays)).toString(
            Formatter.DAY_CODE_PATTERN
        ) == mToday
    }

    private val monthName: String
        get() {
            var month = Formatter.getMonthName(context, mTargetDate.monthOfYear)
            val targetYear = mTargetDate.toString(YEAR_PATTERN)
            if (targetYear != DateTime().toString(YEAR_PATTERN)) {
                month += " $targetYear"
            }
            return month
        }

    private fun gotEvents(events: List<Event>) {
        mEvents = events
        getDays(true)
    }

    private fun getEvents(startTS: Long, endTS: Long): List<Event> =
        mEvents.filter { event -> event.startTS >= startTS && event.endTS <= endTS }
}

fun DateTime.seconds() = millis / 1000L