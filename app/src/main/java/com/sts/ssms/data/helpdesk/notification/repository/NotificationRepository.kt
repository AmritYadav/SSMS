package com.sts.ssms.data.helpdesk.notification.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.notification.api.toSsmsNotification
import com.sts.ssms.ui.helpdesk.notification.model.NotificationResult

class NotificationRepository(private val remoteDataSource: NotificationRemoteDataSource) {

    suspend fun getNotifications(): NotificationResult =
        when (val result = remoteDataSource.notifications()) {
            is Result.Success -> NotificationResult(
                notification = result.data.toSsmsNotification()
            )
            is Result.Error -> NotificationResult(
                error = result.exception.message
            )
        }

}