package com.sts.ssms.data.helpdesk.photogallery.api

import com.sts.ssms.data.common.CommonApiResponse
import com.sts.ssms.data.helpdesk.photogallery.api.model.AlbumApiResponse
import com.sts.ssms.data.helpdesk.photogallery.api.model.PhotoGalleryApiResponse
import com.sts.ssms.data.search.api.SearchApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoGalleryApi : SearchApi {

    @GET("/v3/album/getList")
    suspend fun albums(
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("search") query: String
    ): Response<CommonApiResponse<PhotoGalleryApiResponse>>

    @GET("/v3/album/getPhotos/{albumId}")
    suspend fun albumPhotos(@Path("albumId") albumId: Int): Response<CommonApiResponse<AlbumApiResponse>>
}