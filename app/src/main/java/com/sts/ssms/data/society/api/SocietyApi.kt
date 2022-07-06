package com.sts.ssms.data.society.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.login.api.model.UserResponse
import com.sts.ssms.data.society.model.MenuResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SocietyApi {

    @GET("v3/getUserAccessData")
    suspend fun requestSocietyList(): Response<CommonApiResponse<UserResponse>>

    @GET("v3/switchSociety/{societyId}")
    suspend fun requestSocietyMenus(@Path("societyId") societyId: String): Response<CommonApiResponse<MenuResponse>>

}