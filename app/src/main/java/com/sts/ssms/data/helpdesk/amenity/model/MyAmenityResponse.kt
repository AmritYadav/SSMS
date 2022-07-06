package com.sts.ssms.data.helpdesk.amenity.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.database.entity.EventEntity
import com.sts.ssms.ui.helpdesk.amenity.model.MyAmenity
import com.sts.ssms.utils.eventFormatter
import com.sts.ssms.utils.getEventDateTimeInSeconds

data class MyAmenityResponse(
    @SerializedName("booking_date_from") val from: String,
    @SerializedName("booking_date_to") val to: String,
    @SerializedName("offer_charges") val offerCharges: Double,
    @SerializedName("monthly_charges") val monthlyCharges: String?,
    @SerializedName("request_approval") val approvalStatus: String,
    @SerializedName("flatAmenity_id") val amenityId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sub_amenities_name") val subAmenityName: String,
    @SerializedName("flatAmenityOfferCharges") val amenityOfferCharges: Int,
    @SerializedName("categoryName") val categoryName: String?
)

fun MyAmenityResponse.toEventEntity(): EventEntity = EventEntity(
    id = 0,
    eventId = amenityId,
    name = name,
    startTs = getEventDateTimeInSeconds(from, eventFormatter()),
    endTs = getEventDateTimeInSeconds(to, eventFormatter())
)

fun MyAmenityResponse.toMyAmenity() = MyAmenity(
    amenityId,
    name,
    subAmenityName,
    approvalStatus,
    monthlyCharges ?: "",
    from,
    to
)