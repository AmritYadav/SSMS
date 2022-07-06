package com.sts.ssms.ui.helpdesk.dashboard.model

data class CommentListResult(
    val comments: List<Comment>? = null,
    val error: String? = null
)