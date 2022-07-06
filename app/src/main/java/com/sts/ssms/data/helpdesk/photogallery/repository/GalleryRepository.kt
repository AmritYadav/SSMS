package com.sts.ssms.data.helpdesk.photogallery.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.photogallery.api.model.toAlbumUiModel
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumPhotoResult
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumResult
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class GalleryRepository(private val remoteDataSource: GalleryRemoteDataSource) :
    SearchRepository<AlbumUiModel> {

    override suspend fun getItem(
        page: Int,
        query: String,
        filterId: Int
    ): SearchResult<AlbumUiModel> {
        return when (val result = remoteDataSource.albums(page, query)) {
            is Result.Success -> SearchResult(itemList = result.data.albums.map { it.toAlbumUiModel() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }

    suspend fun albumList(page: Int): AlbumResult {
        return when (val result = remoteDataSource.albums(page)) {
            is Result.Success -> AlbumResult(albumList = result.data.albums.map { it.toAlbumUiModel() })
            is Result.Error -> AlbumResult(error = result.exception.message)
        }
    }

    suspend fun albumPhotos(albumId: Int): AlbumPhotoResult =
        when (val result = remoteDataSource.albumPhotos(albumId)) {
            is Result.Success -> AlbumPhotoResult(albumUiModel = result.data.toAlbumUiModel())
            is Result.Error -> AlbumPhotoResult(error = result.exception.message)
        }
}