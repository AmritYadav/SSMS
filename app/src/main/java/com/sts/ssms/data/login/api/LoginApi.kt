package com.sts.ssms.data.login.api

import com.google.gson.JsonObject
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.login.api.model.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/token")
    suspend fun requestLogin(@Body loginRequest: JsonObject): Response<CommonApiResponse<UserResponse>>
}