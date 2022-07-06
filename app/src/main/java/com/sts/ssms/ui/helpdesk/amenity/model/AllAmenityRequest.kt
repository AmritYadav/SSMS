package com.sts.ssms.ui.helpdesk.amenity.model

import com.sts.ssms.ui.search.model.SearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AllAmenityRequest(
    override val id: Int,
    val amenityName: String,
    val bookingFrom: String,
    val bookingTo: String
): SearchItem