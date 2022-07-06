package com.sts.ssms.ui.helpdesk.vendor.model

data class VendorResult(
    val vendorList: List<VendorUiModel>? = null,
    val error: String? = null
)