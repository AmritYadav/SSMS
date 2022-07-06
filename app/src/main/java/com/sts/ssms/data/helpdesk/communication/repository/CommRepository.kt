package com.sts.ssms.data.helpdesk.communication.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.communication.api.CommCommentRequest
import com.sts.ssms.data.helpdesk.communication.api.toCommunication
import com.sts.ssms.ui.helpdesk.dashboard.model.CommentResult
import com.sts.ssms.ui.helpdesk.officialcommunication.models.CommunicationResult
import com.sts.ssms.ui.helpdesk.officialcommunication.models.CommunicationsResult

class CommRepository(private val remoteDataSource: CommRemoteDataSource) {

    suspend fun communicationsList() = when (val result = remoteDataSource.communications()) {
        is Result.Success -> CommunicationsResult(communications = result.data.map { it.toCommunication() })
        is Result.Error -> CommunicationsResult(error = result.exception.message)
    }

    suspend fun communication(commId: Int) =
        when (val result = remoteDataSource.communication(commId)) {
            is Result.Success -> CommunicationResult(communication = result.data.toCommunication())
            is Result.Error -> CommunicationResult(error = result.exception.message)
        }

    suspend fun commentOnLetter(letterId: Int, comment: String): CommentResult =
        when (val response = remoteDataSource.comment(CommCommentRequest(letterId, comment))) {
            is Result.Success -> CommentResult(success = response.data.success)
            is Result.Error -> CommentResult(error = response.exception.message)
        }
}