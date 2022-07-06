package com.sts.ssms.data.helpdesk.conversations.api

data class PostReplyResponse(
    val postReplyList: List<PostReply>? = null,
    val error: String? = null
)