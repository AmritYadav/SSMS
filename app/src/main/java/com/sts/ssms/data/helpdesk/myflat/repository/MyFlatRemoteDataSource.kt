package com.sts.ssms.data.helpdesk.myflat.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.myflat.api.MyFlatApi
import com.sts.ssms.data.helpdesk.myflat.api.MyFlatResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class MyFlatRemoteDataSource(private val myFlatApi: MyFlatApi) {

    suspend fun members(flatId: Int): Result<MyFlatResponse> = safeApiCall(
        call = { requestFlatMembers(flatId) },
        errorMessage = "Error loading Flat Members list"
    )

    private suspend fun requestFlatMembers(flatId: Int): Result<MyFlatResponse> {
        val apiResponse = myFlatApi.members(flatId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}