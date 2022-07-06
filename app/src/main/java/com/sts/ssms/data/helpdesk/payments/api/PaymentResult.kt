package com.sts.ssms.data.helpdesk.payments.api

import com.sts.ssms.ui.helpdesk.paymentdetails.model.PaymentUiModel

data class PaymentResult(
    val paymentList: List<PaymentUiModel>? = null,
    val error: String? = null
)