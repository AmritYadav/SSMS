package com.sts.ssms.ui.helpdesk.conversations.model.reply

import com.sts.ssms.ui.helpdesk.conversations.model.post.PostReplyUiModel

data class PostReplyResult(
    val replyList: List<PostReplyUiModel>? = null,
    val adapterPosition: Int = -1,
    val success: String? = null,
    val error: String? = null
)