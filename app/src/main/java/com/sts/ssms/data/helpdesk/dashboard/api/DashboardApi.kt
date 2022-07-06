package com.sts.ssms.data.helpdesk.dashboard.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.dashboard.api.model.CommentResponse
import com.sts.ssms.data.helpdesk.dashboard.api.model.TicketApiResponse
import com.sts.ssms.data.helpdesk.dashboard.api.model.TicketCommentRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DashboardApi {

    @GET("/v3/helpDesk/getTicketList")
    suspend fun tickets(): Response<CommonApiResponse<List<TicketApiResponse>>>

    @POST("/v3/helpDesk/saveComment")
    suspend fun commentOnTicket(@Body comment: TicketCommentRequest): Response<CommonApiResponse<SaveItemResponse>>

    @GET("/v3/helpDesk/getCommentList/{ticketId}")
    suspend fun comments(@Path("ticketId") ticketId: Int): Response<CommonApiResponse<List<CommentResponse>>>
}