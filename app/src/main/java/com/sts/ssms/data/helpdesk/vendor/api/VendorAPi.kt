package com.sts.ssms.data.helpdesk.vendor.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.search.api.SearchApi
import retrofit2.Response
import retrofit2.http.*

interface VendorAPi : SearchApi {

    @GET("/v3/vendor/getList")
    suspend fun vendorList(
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("search") query: String
    ): Response<CommonApiResponse<VendorApiResponse>>

    @POST("/v3/vendor/saveRatingsComments")
    suspend fun vendorRatingComment(@Body request: VendorRatingRequest): Response<CommonApiResponse<SaveItemResponse>>

    @GET("/v3/vendor/getList/{vendorId}")
    suspend fun vendor(@Path("vendorId") vendorId: Int): Response<CommonApiResponse<VendorDataResponse>>
}