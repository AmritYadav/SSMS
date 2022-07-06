package com.sts.ssms.data.helpdesk.payments.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.payments.api.PaymentResult
import com.sts.ssms.data.helpdesk.payments.api.toPaymentUiModel
import com.sts.ssms.paging.payments.model.BillDownloadResult

class PaymentRepository(private val remoteDataSource: PaymentRemoteDataSource) {

    suspend fun fetchPayments(page: Int): PaymentResult {
        val result = remoteDataSource.paymentDetails(page)
        return if (result is Result.Success)
            PaymentResult(paymentList = result.data.map { it.toPaymentUiModel() })
        else PaymentResult(error = (result as Result.Error).exception.message)
    }

    suspend fun billDownload(paymentId: Int): BillDownloadResult {
        return when (val result = remoteDataSource.downloadBill(paymentId)) {
            is Result.Success -> result.data
            is Result.Error -> BillDownloadResult(error = result.exception.message)
        }
    }
}