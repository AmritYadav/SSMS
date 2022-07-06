package com.sts.ssms.ui.helpdesk.amenity.model

data class SocietyAmenitiesResult(
    val allAmenities: List<SocietyAmenity>? = null,
    val error: String? = null
)