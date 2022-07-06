package com.sts.ssms.data.helpdesk.payments.api

import com.sts.ssms.data.common.CommonApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PaymentApi {

    @GET("/v3/payment/getList")
    suspend fun paymentsList(@Query("page") pageIndex: Int, @Query("per_page") perPage: Int)
            : Response<CommonApiResponse<PaymentApiResponse>>

    @GET("/v3/payment/downloadPdfBill/{paymentId}")
    suspend fun downloadPdfBill(@Path("paymentId") paymentId: Int): ResponseBody
}