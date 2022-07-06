package com.sts.ssms.data.password.change

import com.sts.ssms.data.common.Result
import com.sts.ssms.ui.password.reset.model.PasswordResult

class ChangePwdRepository(private val changePwdRemoteDataSource: ChangePwdRemoteDataSource) {

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String,
        confirmPassword: String
    ): PasswordResult {
        val result =
            changePwdRemoteDataSource.changePassword(oldPassword, newPassword, confirmPassword)
        if (result is Result.Success)
            return PasswordResult(success = result.data.success)
        result as Result.Error
        return PasswordResult(error = result.exception.message)
    }

}