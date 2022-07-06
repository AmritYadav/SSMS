package com.sts.ssms.data.helpdesk.vendor.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import kotlinx.android.parcel.Parcelize
import java.util.*

fun VendorDataResponse.toVendorUiModel(): VendorUiModel {
    return VendorUiModel(
        id, name, phoneNumber, categoryName,
        website ?: "", email ?: "", info ?: "", address ?: "",
        status, overallRating, ratingsAndComments
    )
}

data class VendorApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<VendorDataResponse> = ArrayList()
)

data class VendorDataResponse(
    val id: Int,
    val societyId: Int,
    val userId: Int,
    val name: String,
    val phoneNumber: String,
    val categoryId: Int,
    val categoryName: String,
    val website: String?,
    val email: String?,
    val info: String?,
    val address: String?,
    val status: String,
    val overallRating: Int,
    val ratingsAndComments: List<RatingAndComment>
)

@Parcelize
data class RatingAndComment(
    val by: String,
    val ratings: Int,
    val comments: String
) : Parcelable