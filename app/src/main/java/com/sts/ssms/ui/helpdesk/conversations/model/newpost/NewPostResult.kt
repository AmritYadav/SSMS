package com.sts.ssms.ui.helpdesk.conversations.model.newpost

import com.sts.ssms.ui.helpdesk.conversations.model.post.PostUiModel

data class NewPostResult(
    val postUiModel: PostUiModel? = null,
    val pos: Int = -1,
    val success: String? = null,
    val error: String? = null
)