package com.sts.ssms.ui.helpdesk.notification.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SsmsNotification(val total: Int, val notificationDetails: List<NotificationDetail>) {
    @Parcelize
    data class NotificationDetail(val title: String, val navItem: Int) : Parcelable
}