package com.sts.ssms.data.helpdesk.notification.api

import com.sts.ssms.data.common.CommonApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface NotificationApi {

    @GET("/v3/getNotifications")
    suspend fun notifications(): Response<CommonApiResponse<NotificationApiResponse>>
}