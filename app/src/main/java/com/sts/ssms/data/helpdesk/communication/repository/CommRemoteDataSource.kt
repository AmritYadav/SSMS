package com.sts.ssms.data.helpdesk.communication.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.communication.api.CommCommentRequest
import com.sts.ssms.data.helpdesk.communication.api.CommunicationApi
import com.sts.ssms.data.helpdesk.communication.api.CommunicationResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class CommRemoteDataSource(private val communicationApi: CommunicationApi) {

    suspend fun communications(): Result<List<CommunicationResponse>> = safeApiCall(
        call = { requestCommunications() },
        errorMessage = "Error loading Communications. Please refresh or retry"
    )

    suspend fun communication(commId: Int): Result<CommunicationResponse> = safeApiCall(
        call = { requestCommunication(commId) },
        errorMessage = "Error loading comments"
    )

    suspend fun comment(commentRequest: CommCommentRequest): Result<SaveItemResponse> =
        safeApiCall(
            call = { commentOnLetter(commentRequest) },
            errorMessage = "Failed to post comment. Please try again"
        )

    private suspend fun requestCommunications(): Result<List<CommunicationResponse>> {
        val apiResponse = communicationApi.communications()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestCommunication(commId: Int): Result<CommunicationResponse> {
        val apiResponse = communicationApi.communication(commId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    if (it.response.conversations.isNullOrEmpty()) it.response.conversations =
                        emptyList()
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun commentOnLetter(commentRequest: CommCommentRequest): Result<SaveItemResponse> {
        val response = communicationApi.addComment(commentRequest)
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }
}