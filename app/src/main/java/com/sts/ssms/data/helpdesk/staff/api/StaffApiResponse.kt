package com.sts.ssms.data.helpdesk.staff.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import kotlinx.android.parcel.Parcelize

fun StaffDataResponse.toStaffUiModel() = StaffUiModel(
    id, societyId, belongsTo ?: "", name, gender, email, description ?: "",
    contact_no, dob, qualification, skills ?: "", language, address, aadharCard, panCard ?: "",
    status, imageUrl ?: "", categoryId, nationality, firstReference ?: "",
    secondReference ?: "", type, categoryName, createdBy, ratingsAndComments, documents, workingAt
)

data class StaffApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<StaffDataResponse>
)

data class StaffDataResponse(
    val id: Int,
    val userId: Int,
    val societyId: Int,
    val belongsTo: String?,
    val name: String,
    val gender: String,
    val email: String,
    val description: String?,
    val contact_no: String,
    val dob: String,
    val qualification: String,
    val skills: String?,
    val language: String,
    val address: String,
    val aadharCard: String,
    val panCard: String?,
    val status: String,
    val imageUrl: String?,
    val categoryId: Int,
    val nationality: String,
    val firstReference: String?,
    val secondReference: String?,
    val type: String,
    val categoryName: String,
    val createdBy: String,
    val ratingsAndComments: List<RatingAndComment> = ArrayList(),
    val documents: List<Document> = ArrayList(),
    val workingAt: List<WorkingAt> = ArrayList()
)

@Parcelize
data class Document(
    val path: String?
) : Parcelable

@Parcelize
data class WorkingAt(
    val phoneNumber: String?,
    val name: String
) : Parcelable