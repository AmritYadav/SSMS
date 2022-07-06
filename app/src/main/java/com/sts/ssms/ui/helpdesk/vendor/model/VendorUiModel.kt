package com.sts.ssms.ui.helpdesk.vendor.model

import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment
import com.sts.ssms.ui.search.model.VendorStaffSearchItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VendorUiModel(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val categoryName: String,
    val website: String,
    val email: String,
    var info: String,
    val address: String,
    override val status: String,
    var overallRating: Int,
    val ratingsAndComments: List<RatingAndComment>
) : VendorStaffSearchItem