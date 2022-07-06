package com.sts.ssms.ui.helpdesk.conversations.model.post

data class PostResult(
    val postList: List<PostUiModel>? = null,
    val error: String? = null
)