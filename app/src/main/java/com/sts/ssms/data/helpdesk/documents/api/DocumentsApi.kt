package com.sts.ssms.data.helpdesk.documents.api

import com.sts.ssms.data.common.CommonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DocumentsApi {

    @GET("/v3/document/getList/{flatId}")
    suspend fun documents(
        @Path("flatId") flatId: Int,
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("search") search: String
    ): Response<CommonApiResponse<DocumentsApiResponse>>
}