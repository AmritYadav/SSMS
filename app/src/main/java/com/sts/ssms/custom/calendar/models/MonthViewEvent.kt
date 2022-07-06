package com.sts.ssms.custom.calendar.models

data class MonthViewEvent(
    val id: Int,
    val title: String,
    val startTS: Long,
    val color: Int,
    val startDayIndex: Int,
    val daysCnt: Int,
    val originalStartDayIndex: Int,
    val isPastEvent: Boolean
)