package com.sts.ssms.data.helpdesk.dashboard.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.dashboard.api.DashboardApi
import com.sts.ssms.data.helpdesk.dashboard.api.model.CommentResponse
import com.sts.ssms.data.helpdesk.dashboard.api.model.TicketApiResponse
import com.sts.ssms.data.helpdesk.dashboard.api.model.TicketCommentRequest
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class DashboardRemoteDataSource(private val dashboardApi: DashboardApi) {

    suspend fun tickets(): Result<List<TicketApiResponse>> = safeApiCall(
        call = { requestTickets() },
        errorMessage = "Error loading tickets"
    )

    suspend fun comment(commentRequest: TicketCommentRequest): Result<SaveItemResponse> =
        safeApiCall(
            call = { commentOnTicket(commentRequest) },
            errorMessage = "Failed to post comment. Please try again"
        )

    suspend fun comments(ticketId: Int): Result<List<CommentResponse>> = safeApiCall(
        call = { requestComments(ticketId) },
        errorMessage = "Error loading comments"
    )

    private suspend fun requestTickets(): Result<List<TicketApiResponse>> {
        val response = dashboardApi.tickets()
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private suspend fun commentOnTicket(commentRequest: TicketCommentRequest): Result<SaveItemResponse> {
        val response = dashboardApi.commentOnTicket(commentRequest)
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private suspend fun requestComments(ticketId: Int): Result<List<CommentResponse>> {
        val response = dashboardApi.comments(ticketId)
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }
}