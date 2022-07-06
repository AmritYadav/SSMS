package com.sts.ssms.ui.helpdesk.noticeboard.models

data class NoticeResult(
    val noticeList: List<Notice>? = null,
    val error: String? = null
)