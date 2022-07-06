package com.sts.ssms.data.helpdesk.vendor.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.vendor.api.VendorAPi
import com.sts.ssms.data.helpdesk.vendor.api.VendorApiResponse
import com.sts.ssms.data.helpdesk.vendor.api.VendorDataResponse
import com.sts.ssms.data.helpdesk.vendor.api.VendorRatingRequest
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class VendorRemoteDataSource(private val vendorAPi: VendorAPi) {

    suspend fun vendors(page: Int, query: String = ""): Result<VendorApiResponse> = safeApiCall(
        call = { requestVendorsList(page, query) },
        errorMessage = "Error loading Vendors List"
    )

    suspend fun vendorRatingComment(request: VendorRatingRequest): Result<SaveItemResponse> =
        safeApiCall(
            call = { rateCommentVendor(request) },
            errorMessage = "Error RatingAndComment and Commenting Vendor"
        )

    suspend fun vendorDetails(vendorId: Int): Result<VendorDataResponse> =
        safeApiCall(
            call = { requestVendor(vendorId) },
            errorMessage = "Error updating Vendor Details"
        )

    private suspend fun requestVendorsList(page: Int, query: String): Result<VendorApiResponse> {
        val vendorResponse = vendorAPi.vendorList(page, 10, query)
        if (vendorResponse.isSuccessful) {
            vendorResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(vendorResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun rateCommentVendor(request: VendorRatingRequest): Result<SaveItemResponse> {
        val apiResponse = vendorAPi.vendorRatingComment(request)
        if (apiResponse.isSuccessful && apiResponse.body() != null)
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    private suspend fun requestVendor(vendorId: Int): Result<VendorDataResponse> {
        val apiResponse = vendorAPi.vendor(vendorId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}