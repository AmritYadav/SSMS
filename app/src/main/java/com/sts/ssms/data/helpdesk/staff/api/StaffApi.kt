package com.sts.ssms.data.helpdesk.staff.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.search.api.SearchApi
import retrofit2.Response
import retrofit2.http.*

interface StaffApi : SearchApi {

    @GET("/v3/staff/getList")
    suspend fun staffList(
        @Query("page") pageIndex: Int, @Query("per_page") perPage: Int,
        @Query("search") query: String
    ): Response<CommonApiResponse<StaffApiResponse>>

    @POST("/v3/staff/saveRatingsComments")
    suspend fun staffRatingComment(@Body request: StaffRatingRequest): Response<CommonApiResponse<SaveItemResponse>>

    @GET("/v3/staff/getList/{staffId}")
    suspend fun staff(@Path("staffId") staffId: Int): Response<CommonApiResponse<StaffDataResponse>>
}