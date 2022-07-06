package com.sts.ssms.data.society.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.login.api.model.toSociety
import com.sts.ssms.data.society.api.SocietyApi
import com.sts.ssms.data.society.model.toFlatEntity
import com.sts.ssms.data.society.model.toMenuEntity
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class SocietyRemoteDataSource(private val societyApi: SocietyApi) {

    suspend fun societies(userId: String, societyId: String?): Result<SocietyResult> =
        safeApiCall(
            call = { requestUserSociety(userId, societyId) },
            errorMessage = "Error fetching Society list"
        )

    private suspend fun requestUserSociety(
        userId: String, societyId: String?
    ): Result<SocietyResult> {
        val apiResponse = societyApi.requestSocietyList()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    val societyList = it.response.societyList
                    if (!societyList.isNullOrEmpty()) {
                        val societyResult = requestUserMenu(societyId ?: societyList[0].id)
                        societyResult.societyList =
                            societyList.map { societyEntity -> societyEntity.toSociety(userId) }
                        Result.Success(societyResult)
                    } else {
                        Result.Error(IOException("No Society Found"))
                    }
                } else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException())
    }

    private suspend fun requestUserMenu(societyId: String): SocietyResult {
        val response = societyApi.requestSocietyMenus(societyId)
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let { commonResponse ->
                return if (commonResponse.isSuccess() && commonResponse.response != null) {
                    SocietyResult(flatList = commonResponse.response.flat.map {
                        it.toFlatEntity(
                            societyId
                        )
                    }, menu = commonResponse.response.menu.toMenuEntity(societyId))
                } else {
                    SocietyResult()
                }
            }
        }
        return SocietyResult()
    }
}