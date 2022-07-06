package com.sts.ssms.data.helpdesk.conversations.api

import com.google.gson.JsonObject
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.ui.helpdesk.conversations.model.reply.PostReplyRequest
import retrofit2.Response
import retrofit2.http.*

interface ConversationApi {

    @GET("/v3/conversation/getPostList")
    suspend fun postList(
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int
    ): Response<CommonApiResponse<PostApiResponse>>

    @GET("/v3/conversation/getPostReplyList/{postId}")
    suspend fun postReplyList(@Path("postId") postId: Int): Response<CommonApiResponse<List<PostReply>>>

    @POST("/v3/conversation/savePost")
    suspend fun postNewConversation(@Body postRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

    @POST("/v3/conversation/likePost")
    suspend fun postLikeUnlike(@Body postRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

    @POST("/v3/conversation/savePostReply")
    suspend fun postNewReply(@Body postReplyRequest: PostReplyRequest): Response<CommonApiResponse<SaveItemResponse>>
}