package com.sts.ssms.data.helpdesk.dashboard.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.dashboard.api.model.TicketCommentRequest
import com.sts.ssms.ui.helpdesk.dashboard.model.CommentListResult
import com.sts.ssms.ui.helpdesk.dashboard.model.TicketResult
import com.sts.ssms.data.helpdesk.dashboard.api.model.toComment
import com.sts.ssms.data.helpdesk.dashboard.api.model.toTicket
import com.sts.ssms.ui.helpdesk.dashboard.model.CommentResult

class DashboardRepository(private val remoteDataSource: DashboardRemoteDataSource) {

    suspend fun ticketsList() = when (val response = remoteDataSource.tickets()) {
        is Result.Success -> TicketResult(
            ticketsList = response.data.map { it.toTicket() })
        is Result.Error -> TicketResult(
            error = response.exception.message
        )
    }

    suspend fun commentOnTicket(ticketId: Int, comment: String): CommentResult =
        when (val response = remoteDataSource.comment(TicketCommentRequest(ticketId, comment))) {
            is Result.Success -> CommentResult(success = response.data.success)
            is Result.Error -> CommentResult(error = response.exception.message)
        }

    suspend fun commentsList(ticketId: Int) =
        when (val response = remoteDataSource.comments(ticketId)) {
            is Result.Success -> CommentListResult(
                comments = response.data.map { it.toComment() })
            is Result.Error -> CommentListResult(
                error = response.exception.message
            )
        }

}