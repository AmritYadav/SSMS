package com.sts.ssms.data.helpdesk.bylaws.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ByLawsApi {
    @GET("/v3/getByLaws")
    suspend fun downloadPdfByLawForm(@Query("form_number") formNumber: Int): ResponseBody
}