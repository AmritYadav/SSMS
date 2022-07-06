package com.sts.ssms.data.helpdesk.societyevent.repository

import com.google.gson.JsonObject
import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.societyevent.api.SocietyEventApi
import com.sts.ssms.data.helpdesk.societyevent.api.SocietyEventApiResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class RemoteSocietyEventDataSource(private val societyEventApi: SocietyEventApi) {

    suspend fun allSocietyEvent(page: Int, perPage: Int): Result<SocietyEventApiResponse> =
        safeApiCall(
            call = { requestAllSocietyEvent(page, perPage) },
            errorMessage = "Error fetching requested amenities"
        )

    suspend fun postSuggestEvent(eventTitle: String, date: String): Result<SaveItemResponse> =
        safeApiCall(
            call = { this.postNewSuggestEvent(eventTitle, date) },
            errorMessage = ""
        )

    private suspend fun requestAllSocietyEvent(
        page: Int,
        perPage: Int
    ): Result<SocietyEventApiResponse> {
        val apiResponse = societyEventApi.getAllSocietyEvent(page, perPage)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))

            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun postNewSuggestEvent(
        eventTitle: String,
        date: String
    ): Result<SaveItemResponse> {
        val response = societyEventApi.postSuggestEvent(buildSuggestEventParams(eventTitle, date))
        if (response.isSuccessful)
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) {
                    Result.Success(it.response)
                } else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private fun buildSuggestEventParams(eventTitle: String, date: String): JsonObject {
        val postParameters = JsonObject()
        postParameters.addProperty("name", eventTitle)
        postParameters.addProperty("date", date)
        return postParameters
    }
}