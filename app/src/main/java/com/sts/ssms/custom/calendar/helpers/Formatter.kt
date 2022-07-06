package com.sts.ssms.custom.calendar.helpers

import android.content.Context
import com.sts.ssms.R
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat

object Formatter {
    const val DAY_CODE_PATTERN = "YYYYMMdd"

    fun getTodayCode() = getDayCodeFromTS(getNowSeconds())

    fun getDateTimeFromCode(dayCode: String): DateTime =
        DateTimeFormat.forPattern(DAY_CODE_PATTERN).withZone(DateTimeZone.UTC).parseDateTime(dayCode)

    private fun getLocalDateTimeFromCode(dayCode: String): DateTime =
        DateTimeFormat.forPattern(DAY_CODE_PATTERN).withZone(DateTimeZone.getDefault()).parseLocalDate(
            dayCode
        ).toDateTimeAtStartOfDay()

    fun getDayEndTS(dayCode: String) =
        getLocalDateTimeFromCode(dayCode).plusDays(1).minusMinutes(1).seconds()

    fun getDayCodeFromDateTime(dateTime: DateTime): String = dateTime.toString(DAY_CODE_PATTERN)

    fun getDateTimeFromTS(ts: Long) = DateTime(ts * 1000L, DateTimeZone.getDefault())

    // use manually translated month names, as DateFormat and Joda have issues with a lot of languages
    fun getMonthName(context: Context, id: Int) = context.getString(months[id - 1])

    fun getDayCodeFromTS(ts: Long): String {
        val dayCode = getDateTimeFromTS(ts).toString(DAY_CODE_PATTERN)
        return if (dayCode.isNotEmpty()) {
            dayCode
        } else {
            "0"
        }
    }

    private val months = arrayListOf(
        R.string.january,
        R.string.february,
        R.string.march,
        R.string.april,
        R.string.may,
        R.string.june,
        R.string.july,
        R.string.august,
        R.string.september,
        R.string.october,
        R.string.november,
        R.string.december
    )

}
