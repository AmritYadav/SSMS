package com.sts.ssms.ui.helpdesk.amenity.model

data class MyAmenity(
    val amenityId: Int,
    val amenity: String,
    val subAmenityName: String,
    val approvalStatus: String,
    val monthlyCharges: String,
    val from: String,
    val to: String,
    var isExpanded: Boolean = false
)