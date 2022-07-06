package com.sts.ssms.ui.helpdesk.conversations.model.reply

import com.google.gson.annotations.SerializedName

data class PostReplyRequest(
    @SerializedName("post_id") val postId: String,
    @SerializedName("text") val comment: String
)