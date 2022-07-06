package com.sts.ssms.ui.helpdesk.staff.model

import com.sts.ssms.data.helpdesk.staff.api.Document
import com.sts.ssms.data.helpdesk.staff.api.WorkingAt
import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment
import com.sts.ssms.ui.search.model.VendorStaffSearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StaffUiModel(
    override val id: Int,
    val societyId: Int,
    val belongsTo: String,
    override val name: String,
    val gender: String,
    val email: String,
    val description: String?,
    override val phoneNumber: String,
    val dob: String,
    val qualification: String,
    val skills: String?,
    val language: String,
    val address: String,
    val aadhaarCard: String,
    val panCard: String?,
    override val status: String,
    val imageUrl: String?,
    val categoryId: Int,
    val nationality: String,
    val firstReference: String?,
    val secondReference: String?,
    val type: String,
    override val categoryName: String,
    val createdBy: String,
    val ratingsAndComments: List<RatingAndComment>,
    val documents: List<Document>,
    val workingAt: List<WorkingAt>
) : VendorStaffSearchItem