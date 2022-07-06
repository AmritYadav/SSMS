package com.sts.ssms.custom.calendar.base

import androidx.fragment.app.Fragment

abstract class BaseCalendarFragment : Fragment() {

    abstract fun goToToday()

    abstract fun refreshEvents()

    abstract fun shouldGoToTodayBeVisible(): Boolean
}