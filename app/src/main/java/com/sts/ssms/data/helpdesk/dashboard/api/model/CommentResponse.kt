package com.sts.ssms.data.helpdesk.dashboard.api.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.dashboard.model.Comment

fun CommentResponse.toComment(): Comment = Comment(
    user.trim(),
    comment.trim(),
    status ?: ""
)

data class CommentResponse(
    @SerializedName("userName") val user: String,
    val comment: String,
    val status: String?
)