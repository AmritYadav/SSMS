package com.sts.ssms.ui.helpdesk.societyevent.model

import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent

data class SocietyEventResult(
    val societyEventList: List<SocietyEvent>? = null,
    val error: String? = null
)