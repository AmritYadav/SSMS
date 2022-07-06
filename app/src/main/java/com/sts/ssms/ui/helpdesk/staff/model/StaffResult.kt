package com.sts.ssms.ui.helpdesk.staff.model

data class StaffResult(
    val staffList: List<StaffUiModel>? = null,
    val error: String? = null
)