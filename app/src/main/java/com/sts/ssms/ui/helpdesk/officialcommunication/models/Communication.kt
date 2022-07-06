package com.sts.ssms.ui.helpdesk.officialcommunication.models

import android.os.Parcelable
import com.sts.ssms.data.helpdesk.communication.api.CommunicationResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Communication(
    val id: Int,
    val to: String,
    val subject: String,
    val emailContent: String,
    val createdOn: String,
    val createdBy: String,
    val status: String,
    val comments: List<CommunicationResponse.Comment>
) : Parcelable