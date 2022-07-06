package com.sts.ssms.data.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

fun AllAmenityRequestData.toAllAmenityRequest() = AllAmenityRequest(
    flatAmenityId,
    amenityName,
    bookingFrom,
    bookingTo
)

data class AllAmenityRequestData(
    @SerializedName("booking_date_from") val bookingFrom: String,
    @SerializedName("booking_date_to") val bookingTo: String,
    @SerializedName("offer_charges") val offerCharge: String?,
    @SerializedName("monthly_charges") val monthlyCharge: String?,
    @SerializedName("request_approval") val approvalStatus: String,
    @SerializedName("flatAmenity_id") val flatAmenityId: Int,
    @SerializedName("name") val amenityName: String,
    @SerializedName("sub_amenities_name") val subAmenityName: String,
    val flatAmenityOfferCharges: Double,
    val categoryName: String?
)