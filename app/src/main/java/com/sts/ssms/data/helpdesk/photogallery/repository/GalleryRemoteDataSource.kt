package com.sts.ssms.data.helpdesk.photogallery.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.photogallery.api.PhotoGalleryApi
import com.sts.ssms.data.helpdesk.photogallery.api.model.AlbumApiResponse
import com.sts.ssms.data.helpdesk.photogallery.api.model.PhotoGalleryApiResponse
import com.sts.ssms.utils.safeApiCall
import java.io.IOException

class GalleryRemoteDataSource(private val photoGalleryApi: PhotoGalleryApi) {

    suspend fun albums(page: Int, query: String = ""): Result<PhotoGalleryApiResponse> =
        safeApiCall(
            call = { requestAlbums(page, query) },
            errorMessage = "Error loading Albums List"
        )

    private suspend fun requestAlbums(page: Int, query: String): Result<PhotoGalleryApiResponse> {
        val apiResponse = photoGalleryApi.albums(page, 30, query)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }

    suspend fun albumPhotos(albumId: Int): Result<AlbumApiResponse> = safeApiCall(
        call = { requestAlbumPhotos(albumId) },
        errorMessage = "Error loading album details"
    )

    private suspend fun requestAlbumPhotos(albumId: Int): Result<AlbumApiResponse> {
        val apiResponse = photoGalleryApi.albumPhotos(albumId)
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.let {
                return if (it.isSuccess() && it.response != null) Result.Success(it.response)
                else Result.Error(IOException(it.errorMessage()))
            }
        }
        return Result.Error(IOException(apiResponse.errorBody()?.charStream().toString()))
    }
}