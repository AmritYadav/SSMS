package com.sts.ssms.data.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.amenity.model.SocietyAmenitiesResponse.SocietyAmenityResult
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyAmenity

fun SocietyAmenityResult.toSocietyAmenity() = SocietyAmenity(
    id,
    name,
    subAmenityName,
    amenityType,
    societyName
)

data class SocietyAmenitiesResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<SocietyAmenityResult>
) {
    data class SocietyAmenityResult(
        val id: Int,
        val tagId: Int,
        @SerializedName("amenity_type") val amenityType: String,
        val societyName: String,
        val name: String,
        val said: Int,
        @SerializedName("sub_amenities_name") val subAmenityName: String
    )
}