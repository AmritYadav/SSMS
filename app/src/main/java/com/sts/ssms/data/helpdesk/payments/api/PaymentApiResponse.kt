package com.sts.ssms.data.helpdesk.payments.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel

fun PaymentApiResult.toPaymentUiModel(): PaymentUiModel {
    return PaymentUiModel(
        id,
        "${wingName?:""}-$flatNumber-$buildingName",
        chequeNumber ?: "",
        billAmount ?: "",
        billDate,
        billDueDate ?: "",
        paymentStatus ?: "",
        paymentMode ?: "",
        paidDate ?: "",
        enableDownload = enableDownload.toInt() == 1
    )
}

data class PaymentApiResponse(
    @SerializedName("total") val totalPages: Int,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<PaymentApiResult> = ArrayList()
)

data class PaymentApiResult(
    @SerializedName("totalFlatChargesId") val id: Int,
    @SerializedName("totalFlatChargeDate") val billDate: String,
    @SerializedName("totalBillCharge") val billAmount: String?,
    @SerializedName("totalFlatChargeDueDate") val billDueDate: String?,
    @SerializedName("billPaymentStatus") val paymentStatus: String?,
    val paymentMode: String?,
    @SerializedName("cheque_number") val chequeNumber: String?,  // Instrument Number
    val paidDate: String?, // Payment Date
    val flatNumber: String?,
    @SerializedName("societyBuildingName") val buildingName: String,
    val wingName: String?,
    @SerializedName("is_visible") val enableDownload: String
)