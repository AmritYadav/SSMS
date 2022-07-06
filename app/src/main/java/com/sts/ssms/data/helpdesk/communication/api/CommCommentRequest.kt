package com.sts.ssms.data.helpdesk.communication.api

import com.google.gson.annotations.SerializedName

data class CommCommentRequest(
    @SerializedName("letter_id") val id: Int,
    @SerializedName("comment") val comment: String
)