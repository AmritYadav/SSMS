package com.sts.ssms.data.helpdesk.amenity.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.amenity.api.AmenityApi
import com.sts.ssms.data.helpdesk.amenity.model.AllAmenityRequestData
import com.sts.ssms.data.helpdesk.amenity.model.AmenityCategoryResponse
import com.sts.ssms.data.helpdesk.amenity.model.AmenityRequestBody
import com.sts.ssms.data.helpdesk.amenity.model.MyAmenityResponse
import com.sts.ssms.data.helpdesk.amenity.model.SocietyAmenitiesResponse.SocietyAmenityResult
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyBuilding
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class RemoteAmenityDataSource(private val amenityApi: AmenityApi) {

    suspend fun allAmenityRequests(page: Int, query: String = "") = safeApiCall(
        call = { allAmenitiesRequest(page, query) },
        errorMessage = "Error loading All Amenities"
    )

    suspend fun myAmenities() = safeApiCall(
        call = { requestMyAmenities() },
        errorMessage = "Error fetching requested amenities"
    )

    suspend fun societyAmenities(page: Int, buildingId: String = "") = safeApiCall(
        call = { requestSocietyAmenities(page, buildingId) },
        errorMessage = "Error loading All Amenities"
    )

    suspend fun societyBuildings() = safeApiCall(
        call = { requestSocietyBuildingList() },
        errorMessage = "Error loading Societies and Building List"
    )

    suspend fun amenityCategory() = safeApiCall(
        call = { requestAmenityCategory() },
        errorMessage = "error loading category"
    )

    suspend fun saveAmenityRequest(request: AmenityRequestBody) = safeApiCall(
        call = { requestAmenity(request) },
        errorMessage = "Error saving your request"
    )

    private suspend fun allAmenitiesRequest(
        pageIndex: Int, query: String = ""
    ): Result<List<AllAmenityRequestData>> {
        val apiResponse = amenityApi.allRequestedAmenities(pageIndex, 20, query)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response.results)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestMyAmenities(): Result<List<MyAmenityResponse>> {
        val apiResponse = amenityApi.myRequestedAmenities()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestSocietyAmenities(
        pageIndex: Int, buildingId: String
    ): Result<List<SocietyAmenityResult>> {
        val response = amenityApi.societyAmenities(pageIndex, 20, buildingId)
        if (response.isSuccessful && response.body() != null) {
            response.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response.results)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(response.errorBody()?.charStream().toString()))
    }

    private suspend fun requestSocietyBuildingList(): Result<List<SocietyBuilding>> {
        val apiResponse = amenityApi.buildingSocietiesList()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestAmenityCategory(): Result<List<AmenityCategoryResponse>> {
        val apiResponse = amenityApi.amenityCategory()
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestAmenity(request: AmenityRequestBody): Result<SaveItemResponse> {
        val apiResponse = amenityApi.requestAmenity(request)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}