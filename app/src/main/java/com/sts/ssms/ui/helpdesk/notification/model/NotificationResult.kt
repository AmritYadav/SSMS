package com.sts.ssms.ui.helpdesk.notification.model

data class NotificationResult(
    val notification: SsmsNotification? = null,
    val error: String? = null
)