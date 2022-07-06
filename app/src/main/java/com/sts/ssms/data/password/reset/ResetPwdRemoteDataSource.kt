package com.sts.ssms.data.password.reset

import com.google.gson.JsonObject
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.password.api.PasswordApi
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class ResetPwdRemoteDataSource(private val passwordApi: PasswordApi) {

    suspend fun resetPassword(
        email: String,
        newPwd: String,
        confirmPwd: String
    ): Result<SaveItemResponse> = safeApiCall(
        call = { requestResetPassword(email, newPwd, confirmPwd) },
        errorMessage = "Error in resetting password"
    )

    private suspend fun requestResetPassword(
        email: String,
        newPwd: String,
        confirmPwd: String
    ): Result<SaveItemResponse> {
        val response = passwordApi.resetPassword(buildResetPwdRequest(email, newPwd, confirmPwd))
        if (response.isSuccessful) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException("Error in resetting password"))
    }

    private fun buildResetPwdRequest(
        email: String,
        newPwd: String,
        confirmPwd: String
    ): JsonObject {
        val resetPwdRequest = JsonObject()
        resetPwdRequest.addProperty("email", email)
        resetPwdRequest.addProperty("password", newPwd)
        resetPwdRequest.addProperty("password_confirmation", confirmPwd)
        return resetPwdRequest
    }
}