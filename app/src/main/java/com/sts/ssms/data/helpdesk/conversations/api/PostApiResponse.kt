package com.sts.ssms.data.helpdesk.conversations.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostReplyUiModel
import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel
import java.util.ArrayList

fun PostApiResult.toPostUiModel(): PostUiModel {
    val postUiModel =
        PostUiModel(
            id,
            firstName,
            title,
            text,
            createdOn,
            totalLikes,
            totalReplies,
            liked.toInt() == 1
        )
    postUiModel.postReplyList = postReplyList?.map { it.toPostReplyUiModel(id) }
    return postUiModel
}

fun PostReply.toPostReplyUiModel(postId: String) =
    PostReplyUiModel(
        postId,
        id,
        firstName,
        text,
        createdOn,
        totalLikes,
        liked.toInt() == 1
    )

data class PostApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val results: List<PostApiResult> = ArrayList()
)

data class PostApiResult(
    @SerializedName("id") val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: String,
    @SerializedName("created_at") val createdOn: String,
    @SerializedName("reply_count") val totalReplies: String,
    @SerializedName("like_count") val totalLikes: String,
    @SerializedName("liked") val liked: String,
    @Expose(serialize = false, deserialize = false)
    var postReplyList: List<PostReply>? = null
)

data class PostReply(
    @SerializedName("id") val id: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("title") val title: String?,
    @SerializedName("text") val text: String,
    @SerializedName("created_at") val createdOn: String,
    @SerializedName("liked") val liked: String,
    @SerializedName("like_count") val totalLikes: String
)