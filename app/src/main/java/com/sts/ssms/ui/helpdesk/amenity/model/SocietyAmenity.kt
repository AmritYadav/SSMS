package com.sts.ssms.ui.helpdesk.amenity.model

data class SocietyAmenity(
    val id: Int,
    val amenity: String,
    val name: String,
    val type: String,
    val building: String,
    val display: String = "$amenity ($type)"
)