package com.sts.ssms.ui.helpdesk.officialcommunication.models

data class CommunicationsResult(
    val communications: List<Communication>? = null,
    val error: String? = null
)