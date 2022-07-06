package com.sts.ssms.data.helpdesk.dashboard.api.model

import com.google.gson.annotations.SerializedName
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket

fun TicketApiResponse.toTicket(): Ticket =
    Ticket(
        id,
        issue,
        createdOn,
        if (ticketStatus == "Progress") "WIP" else ticketStatus,
        isUrgent,
        categoryName,
        createdBy,
        flat,
        ticketStatus != "Progress" && ticketStatus != "Onhold" && ticketStatus != "Closed",
        ticketStatus == "Closed"
    )

data class TicketApiResponse(
    val id: Int,
    val issue: String,
    val createdOn: String,
    val ticketStatus: String,
    val isUrgent: String,
    val categoryName: String,
    @SerializedName("userName") val createdBy: String,
    val flat: String
)