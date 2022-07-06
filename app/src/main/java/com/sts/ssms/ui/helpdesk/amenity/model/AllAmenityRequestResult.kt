package com.sts.ssms.ui.helpdesk.amenity.model

data class AllAmenityRequestResult(
    val allRequests: List<AllAmenityRequest>? = null,
    val error: String? = null
)