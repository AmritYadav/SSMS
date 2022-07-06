package com.sts.ssms.data.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName

fun AmenityCategoryResponse.toAmenityCategory() = AmenityCategory(
    amenityId,
    subAmenityId,
    amenityType,
    itemId ?: 0,
    monthlyCharge ?: 0,
    "$buildingOrSocietyName-$amenityName-$buildingOrSocietyAmenityName"
)

data class AmenityCategoryResponse(
    @SerializedName("amenity_type") val amenityType: String,
    @SerializedName("charges") val charge: String?,
    @SerializedName("BuildingOrSocietyAmenityID") val amenityId: Int,
    @SerializedName("BuildingOrSocietyName") val buildingOrSocietyName: String,
    @SerializedName("AmenityName") val amenityName: String,
    @SerializedName("BuildingOrSocietyAmenityName") val buildingOrSocietyAmenityName: String,
    @SerializedName("amenityTagSubAmenityId") val subAmenityId: Int,
    @SerializedName("monthlyCharges") val monthlyCharge: Int?,
    @SerializedName("itemId") val itemId: Int?
)

data class AmenityCategory(
    val amenityId: Int,
    val subAmenityId: Int,
    val amenityType: String,
    val itemId: Int,
    val monthlyCharge: Int,
    val displayName: String // BuildingOrSocietyName-AmenityName-BuildingOrSocietyAmenityName
) {
    override fun toString(): String {
        return displayName
    }
}