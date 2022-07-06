package com.sts.ssms.custom.calendar.models

import com.sts.ssms.custom.calendar.helpers.FLAG_IS_PAST_EVENT
import com.sts.ssms.custom.calendar.helpers.Formatter
import com.sts.ssms.custom.calendar.helpers.getNowSeconds
import com.sts.ssms.utils.addBitIf

data class Event(
    val id: Int,
    val startTS: Long,
    val endTS: Long,
    val title: String,
    var flags: Int = 0
) {

    fun updateIsPastEvent() {
        val endTSToCheck = if (startTS < getNowSeconds()) {
            Formatter.getDayEndTS(Formatter.getDayCodeFromTS(endTS))
        } else {
            endTS
        }
        isPastEvent = endTSToCheck < getNowSeconds()
    }

    var isPastEvent: Boolean
        get() = flags and FLAG_IS_PAST_EVENT != 0
        set(isPastEvent) {
            flags = flags.addBitIf(isPastEvent, FLAG_IS_PAST_EVENT)
        }

}