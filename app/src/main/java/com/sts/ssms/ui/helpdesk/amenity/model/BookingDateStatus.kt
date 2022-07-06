package com.sts.ssms.ui.helpdesk.amenity.model

data class BookingDateStatus(
    val startDateError: Int? = null,
    val startTimeError: Int? = null,
    val isDataValid: Boolean = false
)