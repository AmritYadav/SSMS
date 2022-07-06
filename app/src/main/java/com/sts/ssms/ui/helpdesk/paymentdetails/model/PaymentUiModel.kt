package com.sts.ssms.ui.helpdesk.paymentdetails.model

data class PaymentUiModel(
    val id: Int,
    val wingFlatBuilding: String,  // Wing-Flat-Building eg. A-1001-2  --or--  A-1001-Building1
    val chequeNumber: String,  // Instrument Number
    val billAmount: String,
    val billDate: String,
    val billDueDate: String,
    val paymentStatus: String,
    val paymentMode: String,
    val paidDate: String, // Payment Date
    val enableDownload: Boolean,
    var isExpanded: Boolean = false
)