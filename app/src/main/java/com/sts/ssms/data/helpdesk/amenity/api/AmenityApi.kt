package com.sts.ssms.data.helpdesk.amenity.api

import com.sts.ssms.data.common.BasePaginationResponse
import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.common.SaveItemResponse
import com.sts.ssms.data.helpdesk.amenity.model.*
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyBuilding
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AmenityApi {

    @GET("/v3/amenities/getList")
    suspend fun societyAmenities(
        @Query("page") pageIndex: Int, @Query("per_page") perPage: Int,
        @Query("building_id") buildingId: String
    ): Response<CommonApiResponse<SocietyAmenitiesResponse>>

    @GET("/v3/amenities/getRequestAmenities")
    suspend fun allRequestedAmenities(
        @Query("page") pageIndex: Int, @Query("per_page") perPage: Int,
        @Query("search") query: String
    ): Response<CommonApiResponse<BasePaginationResponse<AllAmenityRequestData>>>

    @GET("/v3/amenities/myRequest")
    suspend fun myRequestedAmenities(): Response<CommonApiResponse<List<MyAmenityResponse>>>

    @GET("/v3/amenities/getBuildingList")
    suspend fun buildingSocietiesList(): Response<CommonApiResponse<List<SocietyBuilding>>>

    @GET("/v3/amenities/getCategoriesList")
    suspend fun amenityCategory(): Response<CommonApiResponse<List<AmenityCategoryResponse>>>

    @POST("/v3/amenities/saveRequest")
    suspend fun requestAmenity(@Body requestBody: AmenityRequestBody): Response<CommonApiResponse<SaveItemResponse>>
}