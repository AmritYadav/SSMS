package com.sts.ssms.data.helpdesk.profile.repository

import com.sts.ssms.data.database.entity.UserEntity
import com.sts.ssms.data.helpdesk.profile.api.ProfileApi
import com.sts.ssms.data.helpdesk.profile.api.toUserEntity
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

import com.sts.ssms.data.common.Result

class ProfileRemoteDataSource(private val profileApi: ProfileApi) {

    suspend fun userProfile(): Result<UserEntity> = safeApiCall(
        call = { requestUserProfile() },
        errorMessage = "Error loading User Profile"
    )

    private suspend fun requestUserProfile(): Result<UserEntity> {
        val response = profileApi.requestProfile()
        if (response.isSuccessful) {
            val body = response.body()
            body?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response.toUserEntity())
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException("Error loading Profile"))
    }

}