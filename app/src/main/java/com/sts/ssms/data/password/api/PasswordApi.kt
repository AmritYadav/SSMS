package com.sts.ssms.data.password.api

import com.google.gson.JsonObject
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PasswordApi {

    @POST("/forgotPassword/resetPassword")
    suspend fun resetPassword(@Body resetPwdRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

    @POST("/v3/changePassword")
    suspend fun changePassword(@Body changePwdRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

    @POST("/forgotPassword/sendOtp")
    suspend fun forgotPassword(@Body forgotPwdRequest: JsonObject): Response<CommonApiResponse<SaveItemResponse>>

    @GET("/forgotPassword/verifyOtp")
    suspend fun verifyOtp(@Query("email") email: String, @Query("otp") otp: String): Response<CommonApiResponse<SaveItemResponse>>
}