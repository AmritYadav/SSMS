package com.sts.ssms.data.login.repository.remote

import com.google.gson.JsonObject
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.database.entity.LoginResponse
import com.sts.ssms.data.login.api.LoginApi
import com.sts.ssms.data.login.api.model.toSociety
import com.sts.ssms.data.login.api.model.toUserEntity
import com.sts.ssms.data.login.repository.AuthTokenLocalDataSource
import com.sts.ssms.data.society.api.SocietyApi
import com.sts.ssms.data.society.model.toFlatEntity
import com.sts.ssms.data.society.model.toMenuEntity
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class LoginRemoteDataSourceImpl(
    private val authTokenLocalDataSource: AuthTokenLocalDataSource,
    private val loginApi: LoginApi,
    private val societyApi: SocietyApi
) : LoginRemoteDataSource {

    override suspend fun login(email: String, password: String): Result<LoginResponse> =
        safeApiCall(
            call = { requestLogin(email, password) },
            errorMessage = "Error logging user"
        )

    private suspend fun requestLogin(email: String, password: String): Result<LoginResponse> {
        val response = loginApi.requestLogin(buildLoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                if (it.isSuccess()) {
                    val userResponse = it.response
                    userResponse?.let {
                        authTokenLocalDataSource.authToken = userResponse.accessToken
                        val passwordChanged = userResponse.passwordChanged
                        val loginResponse = LoginResponse(
                            userEntity = userResponse.toUserEntity(),
                            passwordChanged = passwordChanged,
                            societyEntityList = userResponse.societyList.map {societyResponse ->
                                societyResponse.toSociety(
                                    userResponse.userId
                                )
                            }
                        )
                        return if (passwordChanged == 1) requestUserMenu(loginResponse)
                        else Result.Success(loginResponse)
                    }
                } else {
                    return Result.Error(IOException(it.errorMessage()))
                }
            }
        }
        return Result.Error(IOException("Throw IOException"))
    }

    private fun buildLoginRequest(email: String, password: String): JsonObject {
        val loginRequest = JsonObject()
        loginRequest.addProperty("email", email)
        loginRequest.addProperty("password", password)
        return loginRequest
    }

    private suspend fun requestUserMenu(loginResponse: LoginResponse): Result<LoginResponse> {
        loginResponse.societyEntityList?.firstOrNull()?.let { society ->
            val response = societyApi.requestSocietyMenus(society.societyId)
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let { commonResponse ->
                    if (commonResponse.isSuccess() && commonResponse.response != null) {
                        loginResponse.menuEntity =
                            commonResponse.response.menu?.toMenuEntity(society.societyId)
                        loginResponse.flatEntityList =
                            commonResponse.response.flat.map { it.toFlatEntity(society.societyId) }
                    }
                }
            }
        }
        return Result.Success(loginResponse)
    }

    override fun logout() {
        authTokenLocalDataSource.authToken = null
    }
}