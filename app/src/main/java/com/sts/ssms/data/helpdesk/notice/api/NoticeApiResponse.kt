package com.sts.ssms.data.helpdesk.notice.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.notice.api.NoticeApiResponse.NoticeResponse
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import java.util.*

fun NoticeResponse.toNotice(): Notice = Notice(
    id,
    title,
    description,
    type,
    name,
    by,
    createdOn,
    expiryDate
)

data class NoticeApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    val type: Int,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val notices: List<NoticeResponse> = ArrayList()
) {
    data class NoticeResponse(
        val id: Int,
        @SerializedName("user_id") val user_id: Int,
        val title: String,
        @SerializedName("text") val description: String,
        val status: Int,
        @SerializedName("society_id") val societyId: Int,
        @SerializedName("approval_status") val approvalStatus: String,
        @SerializedName("name") val name: String,
        @SerializedName("type") val type: Int,
        @SerializedName("expiry_date") val expiryDate: String,
        @SerializedName("reply_to_email") val replyToEmail: String?,
        @SerializedName("created_at") val createdOn: String,
        val by: String
    )
}