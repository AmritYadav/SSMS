package com.sts.ssms.data.helpdesk.notification.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.notification.api.NotificationApi
import com.sts.ssms.data.helpdesk.notification.api.NotificationApiResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class NotificationRemoteDataSource(private val notificationApi: NotificationApi) {

    suspend fun notifications(): Result<NotificationApiResponse> = safeApiCall(
        call = { requestNotifications() },
        errorMessage = ""
    )

    private suspend fun requestNotifications(): Result<NotificationApiResponse> {
        val apiResponse = notificationApi.notifications()
        if (apiResponse.isSuccessful)
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

}