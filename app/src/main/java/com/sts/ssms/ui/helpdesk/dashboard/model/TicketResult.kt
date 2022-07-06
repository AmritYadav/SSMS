package com.sts.ssms.ui.helpdesk.dashboard.model

data class TicketResult(
    val ticketsList: List<Ticket>? = null,
    val error: String? = null
)