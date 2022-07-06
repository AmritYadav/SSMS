package com.sts.ssms.data.password.reset

import com.sts.ssms.data.common.Result
import com.sts.ssms.ui.password.reset.model.PasswordResult

class ResetPwdRepository(private val resetPwdRemoteDataSource: ResetPwdRemoteDataSource) {

    suspend fun resetPassword(email: String, newPwd: String, confirmPwd: String): PasswordResult {
        val result = resetPwdRemoteDataSource.resetPassword(email, newPwd, confirmPwd)
        if (result is Result.Success)
            return PasswordResult(success = result.data.success)
        result as Result.Error
        return PasswordResult(error = result.exception.message)
    }

}