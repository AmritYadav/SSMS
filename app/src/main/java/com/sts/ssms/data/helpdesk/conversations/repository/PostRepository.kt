package com.sts.ssms.data.helpdesk.conversations.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.conversations.api.toPostReplyUiModel
import com.sts.ssms.data.helpdesk.conversations.api.toPostUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.newpost.NewPostResult
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostResult
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyResult

class PostRepository(private val remoteDataSource: PostRemoteDataSource) {

    suspend fun getPostList(pageIndex: Int): PostResult {
        val result = remoteDataSource.getPostList(pageIndex)
        return if (result is Result.Success) {
            PostResult(postList = result.data.results.map { it.toPostUiModel() })
        } else {
            PostResult(error = ((result as Result.Error).exception.message))
        }
    }

    suspend fun postConversation(title: String, description: String): NewPostResult {
        val result = remoteDataSource.postConversation(title, description)
        if (result is Result.Success)
            return NewPostResult(
                success = result.data.success
            )
        result as Result.Error
        return NewPostResult(error = result.exception.message)
    }

    suspend fun postLikeUnLike(postId: String, liked: Boolean): NewPostResult {
        val result = remoteDataSource.postLike(postId, liked)
        if (result is Result.Success)
            return NewPostResult(
                success = result.data.success
            )
        result as Result.Error
        return NewPostResult(error = result.exception.message)
    }

    suspend fun postNewLike(postReplyRequest: PostReplyRequest): PostReplyResult {
        val result = remoteDataSource.postReply(postReplyRequest)
        if (result is Result.Success)
            return PostReplyResult(
                replyList = result.data.postReplyList?.map { it.toPostReplyUiModel(postReplyRequest.postId) },
                success = "Post Reply Success"
            )
        result as Result.Error
        return PostReplyResult(error = result.exception.message)
    }
}