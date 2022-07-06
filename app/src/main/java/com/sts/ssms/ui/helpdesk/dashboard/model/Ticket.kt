package com.sts.ssms.ui.helpdesk.dashboard.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticket(
    val id: Int,
    val issue: String,
    val createdOn: String,
    val ticketStatus: String,
    val isUrgent: String,
    val categoryName: String,
    val createdBy: String,
    val flat: String,
    val isOpen: Boolean,
    val isClosed: Boolean
) : Parcelable