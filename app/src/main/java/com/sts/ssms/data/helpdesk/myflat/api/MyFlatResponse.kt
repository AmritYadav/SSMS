package com.sts.ssms.data.helpdesk.myflat.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.myflat.model.MyFlatUiModel

data class MyFlatResponse(
    @SerializedName("members") val memberList: List<FlatMemberResponse>,
    @SerializedName("tenant") val tenantList: List<FlatMemberResponse>
)

fun FlatMemberResponse.toMyFlatUiModel(isOwner: Boolean): MyFlatUiModel {
    return MyFlatUiModel(
        id,
        flatId,
        "$firstName $lastName",
        dob ?: "",
        voterId ?: "",
        contactNo ?: "",
        email ?: "",
        associateMember.toInt() == 1,
        panCard ?: "",
        aadharCard ?: "",
        photoApprovalStatus ?: "",
        imageUrl ?: "",
        relation ?: "",
        isOwner
    )
}

data class FlatMemberResponse(
    val id: Int,
    val flatId: Int,
    val firstName: String,
    val lastName: String,
    val dob: String?,
    val voterId: String?,
    val contactNo: String?,
    val email: String?,
    val associateMember: String,
    val panCard: String?,
    val aadharCard: String?,
    val photoApprovalStatus: String?,
    val imageUrl: String?,
    val relation: String?
)