package com.sts.ssms.data.helpdesk.notice.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeType

fun NoticeTypeResponse.toNoticeType() = NoticeType(id, name)

data class NoticeTypeResponse(
    val id: Int,
    val name: String,
    val description: String?,
    val type: String,
    @SerializedName("society_id") val societyId: Int,
    @SerializedName("is_mandatory") val isMandatory: Int
)