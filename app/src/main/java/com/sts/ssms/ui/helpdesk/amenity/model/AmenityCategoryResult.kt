package com.sts.ssms.ui.helpdesk.amenity.model

import com.sts.ssms.data.helpdesk.amenity.model.AmenityCategory

data class AmenityCategoryResult(
    val categories: List<AmenityCategory>? = null,
    val error: String? = null
)