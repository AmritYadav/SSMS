package com.sts.ssms.ui.helpdesk.noticeboard.models

data class NoticeType(
    val id: Int,
    val name: String
) {
    override fun toString(): String {
        return name
    }
}