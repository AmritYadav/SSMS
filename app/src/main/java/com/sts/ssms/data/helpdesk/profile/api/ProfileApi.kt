package com.sts.ssms.data.helpdesk.profile.api

import com.sts.ssms.data.common.CommonApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProfileApi {

    @GET("/v3/getUserProfile")
    suspend fun requestProfile(): Response<CommonApiResponse<ProfileResponse>>
}