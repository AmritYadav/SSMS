package com.sts.ssms.data.helpdesk.staff.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.staff.api.StaffApi
import com.sts.ssms.data.helpdesk.staff.api.StaffApiResponse
import com.sts.ssms.data.helpdesk.staff.api.StaffDataResponse
import com.sts.ssms.data.helpdesk.staff.api.StaffRatingRequest
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class StaffRemoteDataSource(private val staffApi: StaffApi) {

    suspend fun staffList(page: Int, query: String = ""): Result<StaffApiResponse> = safeApiCall(
        call = { requestStaffList(page, query) },
        errorMessage = "Error loading Staff List"
    )

    suspend fun staffRatingComment(request: StaffRatingRequest): Result<SaveItemResponse> =
        safeApiCall(
            call = { rateCommentStaff(request) },
            errorMessage = "Error RatingAndComment and Commenting Staff"
        )

    suspend fun staffDetails(staffId: Int): Result<StaffDataResponse> =
        safeApiCall(
            call = { requestStaff(staffId) },
            errorMessage = "Error updating Staff Details"
        )

    private suspend fun requestStaffList(page: Int, query: String): Result<StaffApiResponse> {
        val vendorResponse = staffApi.staffList(page, 10, query)
        if (vendorResponse.isSuccessful) {
            vendorResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(vendorResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun rateCommentStaff(request: StaffRatingRequest): Result<SaveItemResponse> {
        val apiResponse = staffApi.staffRatingComment(request)
        if (apiResponse.isSuccessful && apiResponse.body() != null)
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestStaff(staffId: Int): Result<StaffDataResponse> {
        val apiResponse = staffApi.staff(staffId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

}