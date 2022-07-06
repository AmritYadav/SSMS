package com.sts.ssms.data.helpdesk.communication.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import kotlinx.android.parcel.Parcelize

fun CommunicationResponse.toCommunication() = Communication(
    id,
    to,
    subject,
    emailText,
    createdOn,
    createdBy,
    status ?: "",
    if (conversations.isNullOrEmpty()) emptyList() else conversations
)

data class CommunicationResponse(
    val id: Int,
    @SerializedName("recepient_id") val recipientId: Int,
    @SerializedName("created_by") val creatorId: Int,
    val subject: String,
    @SerializedName("text") val emailText: String,
    @SerializedName("role_name") val to: String,
    @SerializedName("first_name") val createdBy: String,
    @SerializedName("created_at") val createdOn: String,
    @SerializedName("updated_at") val updatedOn: String?,
    val status: String?,
    @SerializedName("is_read") val isRead: Int,
    var conversations: List<Comment>
) {
    @Parcelize
    data class Comment(
        val id: Int,
        val comment: String,
        @SerializedName("first_name") val by: String
    ) : Parcelable
}