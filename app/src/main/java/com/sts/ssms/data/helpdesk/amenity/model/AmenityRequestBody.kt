package com.sts.ssms.data.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName

data class AmenityRequestBody(
    @SerializedName("amenity_tags_id") val amenityId: Int,
    @SerializedName("subamenity_id") val subAmenityId: Int,
    @SerializedName("amenity_type") val amenityType: String,
    @SerializedName("item_id") val itemId: Int,
    @SerializedName("monthly_charges") val monthlyCharge: Int,
    @SerializedName("booking_date_from") val bookingDateFrom: String,
    @SerializedName("booking_date_to") val bookingDateTo: String
)