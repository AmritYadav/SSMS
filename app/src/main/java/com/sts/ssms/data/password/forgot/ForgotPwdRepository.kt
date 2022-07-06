package com.sts.ssms.data.password.forgot

import com.sts.ssms.data.common.Result
import com.sts.ssms.ui.password.reset.model.PasswordResult

class ForgotPwdRepository(private val remoteDataSource: ForgotPwdRemoteDataSource) {

    suspend fun requestOtp(email: String): PasswordResult {
        return when (val result = remoteDataSource.requestOtp(email)) {
            is Result.Success -> result.data
            is Result.Error -> PasswordResult(error = result.exception.message)
        }
    }

    suspend fun verifyOtp(email: String, otp: String): PasswordResult {
        return when (val result = remoteDataSource.verifyOtp(email, otp)) {
            is Result.Success -> result.data
            is Result.Error -> PasswordResult(error = result.exception.message)
        }
    }

    companion object {
        const val KEY_USER_EMAIL = "USER_EMAIL"
    }
}