package com.sts.ssms.data.password.forgot

import com.google.gson.JsonObject
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.password.api.PasswordApi
import com.sts.ssms.ui.password.reset.model.PasswordResult
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class ForgotPwdRemoteDataSource(private val passwordApi: PasswordApi) {

    suspend fun requestOtp(email: String): Result<PasswordResult> = safeApiCall(
        call = { sendOtp(email) },
        errorMessage = "Error sending OTP. Please check your Internet or user id"
    )

    suspend fun verifyOtp(email: String, otp: String): Result<PasswordResult> = safeApiCall(
        call = { verifyOtpRequest(email, otp) },
        errorMessage = "Error verifying the OTP"
    )

    private suspend fun sendOtp(email: String): Result<PasswordResult> {
        val response = passwordApi.forgotPassword(buildRequest(email))
        if (response.isSuccessful) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(PasswordResult(success = it.response.success))
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private fun buildRequest(email: String): JsonObject {
        val request = JsonObject()
        request.addProperty("email", email)
        return request
    }

    private suspend fun verifyOtpRequest(email: String, otp: String): Result<PasswordResult> {
        val response = passwordApi.verifyOtp(email, otp)
        if (response.isSuccessful) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(PasswordResult(success = it.response.success))
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }
}