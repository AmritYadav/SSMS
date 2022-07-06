package com.sts.ssms.data.helpdesk.societyevent.api

import com.google.gson.annotations.SerializedName
import com.sts.ssms.data.helpdesk.societyevent.api.SocietyEventApiResponse.SocietyEventResponse
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent

data class SocietyEventApiResponse(
    @SerializedName("total") val totalPages: String,
    @SerializedName("per_page") val perPage: String,
    @SerializedName("current_page") val page: Int,
    @SerializedName("data") val societyEventList: List<SocietyEventResponse>
) {
    data class SocietyEventResponse(
        val societyEventId: Int,
        val title: String,
        val date: String
    )
}

fun SocietyEventResponse.toSocietyEvent(): SocietyEvent = SocietyEvent(
    id = societyEventId,
    title = title,
    date = date
)