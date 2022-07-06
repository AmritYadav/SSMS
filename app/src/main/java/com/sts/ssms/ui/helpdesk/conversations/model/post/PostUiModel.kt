package com.sts.ssms.ui.helpdesk.conversations.model.post

data class PostUiModel(
    val postId: String,
    val firstName: String,
    val title: String,
    val content: String,
    val createdOn: String,
    var totalLikes: String,
    var totalReplies: String,
    var liked: Boolean,
    var postReplyList: List<PostReplyUiModel>? = null
)

data class PostReplyUiModel(
    val parentPostId: String,
    val postReplyId: String,
    val firstName: String,
    val desc: String,
    val createdOn: String,
    var totalLikes: String,
    var liked: Boolean
)