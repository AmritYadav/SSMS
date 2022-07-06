package com.sts.ssms.data.login.repository.remote

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.database.entity.LoginResponse

interface LoginRemoteDataSource {
    suspend fun login(email: String, password: String): Result<LoginResponse>
    fun logout()
}