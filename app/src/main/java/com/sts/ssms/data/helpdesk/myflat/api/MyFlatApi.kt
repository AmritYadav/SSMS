package com.sts.ssms.data.helpdesk.myflat.api

import com.sts.ssms.data.common.CommonApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MyFlatApi {

    @GET("/v3/myFlat/memberList/{flatId}")
    suspend fun members(@Path("flatId") id: Int): Response<CommonApiResponse<MyFlatResponse>>

}