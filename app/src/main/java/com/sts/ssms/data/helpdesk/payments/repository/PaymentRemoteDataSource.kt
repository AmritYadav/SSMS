package com.sts.ssms.data.helpdesk.payments.repository

import com.google.gson.JsonParser
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.payments.api.PaymentApi
import com.sts.ssms.data.helpdesk.payments.api.PaymentApiResult
import com.sts.ssms.paging.payments.model.BillDownloadResult
import com.sts.ssms.utils.safeApiCall
import java.io.IOException


class PaymentRemoteDataSource(private val paymentApi: PaymentApi) {

    suspend fun paymentDetails(page: Int): Result<List<PaymentApiResult>> = safeApiCall(
        call = { requestPaymentList(page) },
        errorMessage = "Error loading payment details"
    )

    suspend fun downloadBill(paymentId: Int): Result<BillDownloadResult> = safeApiCall(
        call = { requestBill(paymentId) },
        errorMessage = "Error downloading bill"
    )

    private suspend fun requestPaymentList(page: Int): Result<List<PaymentApiResult>> {
        val response = paymentApi.paymentsList(page, 10)
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response.results)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private suspend fun requestBill(paymentId: Int): Result<BillDownloadResult> {
        val response = paymentApi.downloadPdfBill(paymentId)
        response.contentType()?.let {
            if (it.subtype == "json") {
                val parser = JsonParser()
                val errorResponse = parser.parse(response.string()).asJsonObject
                val errorMsg = errorResponse.getAsJsonArray("fault").joinToString(separator = ".")
                return Result.Error(IOException(errorMsg))
            }
        }
        return Result.Success(BillDownloadResult(success = response.byteStream()))
    }
}