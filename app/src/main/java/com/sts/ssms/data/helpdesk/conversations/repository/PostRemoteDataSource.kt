package com.sts.ssms.data.helpdesk.conversations.repository

import com.google.gson.JsonObject
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.conversations.api.*
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import com.sts.ssms.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException

class PostRemoteDataSource(private val conversationsApi: ConversationApi) {

    suspend fun getPostList(pageIndex: Int): Result<PostApiResponse> = safeApiCall(
        call = { requestPostList(pageIndex) },
        errorMessage = "Error fetching conversations"
    )

    suspend fun postConversation(title: String, description: String): Result<SaveItemResponse> =
        safeApiCall(
            call = { postNewConversation(title, description) },
            errorMessage = ""
        )

    suspend fun postLike(postId: String, liked: Boolean): Result<SaveItemResponse> =
        safeApiCall(
            call = { postLikes(postId, liked) },
            errorMessage = "Error updating like/Dislike status"
        )

    suspend fun postReply(postReplyRequest: PostReplyRequest): Result<PostReplyResponse> =
        safeApiCall(
            call = { postNewReply(postReplyRequest) },
            errorMessage = "Unable to post reply"
        )

    private suspend fun requestPostList(pageIndex: Int): Result<PostApiResponse> {
        val response = conversationsApi.postList(pageIndex, 10)
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    val postApiResponse = preparePostReplyList(it.response)
                    Result.Success(postApiResponse)
                } else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private fun preparePostReplyList(postApiResponse: PostApiResponse): PostApiResponse =
        runBlocking {
            if (!postApiResponse.results.isNullOrEmpty()) {
                postApiResponse.results.map {
                    if (it.totalReplies.toInt() > 0) {
                        withContext(Dispatchers.Default) {
                            it.postReplyList = requestPostReplyList(it.id.toInt())
                        }
                    }
                }
            }
            return@runBlocking postApiResponse
        }

    private suspend fun requestPostReplyList(postId: Int): List<PostReply> {
        val response = conversationsApi.postReplyList(postId)
        return if (response.isSuccessful && response.body() != null) {
            val commonResponse = (response.body() as CommonApiResponse)
            if (commonResponse.isSuccess()) commonResponse.response!!
            else emptyList()
        } else {
            emptyList()
        }
    }

    private suspend fun postNewConversation(
        title: String,
        description: String
    ): Result<SaveItemResponse> {
        val response = conversationsApi.postNewConversation(buildNewPostParams(title, description))
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private fun buildNewPostParams(title: String, description: String): JsonObject {
        val postParameters = JsonObject()
        postParameters.addProperty("title", title)
        postParameters.addProperty("text", description)
        return postParameters
    }

    private suspend fun postLikes(postId: String, liked: Boolean): Result<SaveItemResponse> {
        val response = conversationsApi.postLikeUnlike(buildPostLikeParams(postId, liked))
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private fun buildPostLikeParams(postId: String, liked: Boolean): JsonObject {
        val postLikeParameters = JsonObject()
        postLikeParameters.addProperty("entity_id", postId)
        postLikeParameters.addProperty("like", liked)
        return postLikeParameters
    }

    private suspend fun postNewReply(postReplyRequest: PostReplyRequest): Result<PostReplyResponse> {
        val response = conversationsApi.postNewReply(postReplyRequest)
        if (response.isSuccessful && response.body() != null)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    val replyList = requestPostReplyList(postReplyRequest.postId.toInt())
                    val postReplyResponse = PostReplyResponse(replyList, it.response.success)
                    Result.Success(postReplyResponse)
                } else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }
}