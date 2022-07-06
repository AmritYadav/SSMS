package com.sts.ssms.data.helpdesk.communication.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunicationApi {

    @GET("/v3/communication/getList?search=")
    suspend fun communications(): Response<CommonApiResponse<List<CommunicationResponse>>>

    @GET("/v3/communication/{id}")
    suspend fun communication(@Path("id") commId: Int): Response<CommonApiResponse<CommunicationResponse>>

    @POST("/v3/communication/saveReply")
    suspend fun addComment(@Body commentRequest: CommCommentRequest): Response<CommonApiResponse<SaveItemResponse>>
}