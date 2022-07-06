package com.sts.ssms.data.helpdesk.societyevent.api

import com.google.gson.JsonObject
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SocietyEventApi {
    @GET("/v3/societyEvent/list")
    suspend fun getAllSocietyEvent(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<CommonApiResponse<SocietyEventApiResponse>>

    @POST("/v3/societyEvent/save")
    suspend fun postSuggestEvent(@Body postRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

}