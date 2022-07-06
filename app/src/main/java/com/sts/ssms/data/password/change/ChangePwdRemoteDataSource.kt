package com.sts.ssms.data.password.change

import com.google.gson.JsonObject
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.password.api.PasswordApi
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class ChangePwdRemoteDataSource(private val passwordApi: PasswordApi) {

    suspend fun changePassword(
        email: String,
        newPwd: String,
        confirmPwd: String
    ): Result<SaveItemResponse> = safeApiCall(
        call = { requestPasswordChange(email, newPwd, confirmPwd) },
        errorMessage = "Error in changing password"
    )

    private suspend fun requestPasswordChange(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Result<SaveItemResponse> {
        val response = passwordApi.changePassword(
            buildResetPwdRequest(
                oldPassword,
                newPassword,
                confirmPassword
            )
        )
        if (response.isSuccessful) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException("Error changing password"))
    }

    private fun buildResetPwdRequest(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): JsonObject {
        val resetPwdRequest = JsonObject()
        resetPwdRequest.addProperty("oldPassword", oldPassword)
        resetPwdRequest.addProperty("newPassword", newPassword)
        resetPwdRequest.addProperty("confirmPassword", confirmPassword)
        return resetPwdRequest
    }

}