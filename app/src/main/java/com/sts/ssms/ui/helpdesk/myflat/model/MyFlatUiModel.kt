package com.sts.ssms.ui.helpdesk.myflat.model

data class MyFlatUiModel(
    val id: Int,
    val flatId: Int,
    val ownerName: String,
    val dob: String,
    val voterId: String,
    val contactNo: String,
    val email: String,
    val isAssociateMember: Boolean,
    val panCard: String,
    val aadhaarCard: String,
    val photoStatus: String?,
    val avatar: String?,
    val relation: String?,
    val isOwner: Boolean,
    var isExpanded: Boolean = false
)