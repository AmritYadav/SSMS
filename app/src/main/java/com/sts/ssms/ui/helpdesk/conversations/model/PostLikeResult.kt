package com.sts.ssms.ui.helpdesk.conversations.model

data class PostLikeResult(
    val postLike: PostLike? = null,
    val success: String? = null,
    val error: String? = null
)

data class PostLike(
    val postId: String,
    var liked: Boolean,
    var totalLikes: Int,
    val postAdapterPosition: Int,
    val isPostLike: Boolean = true
)