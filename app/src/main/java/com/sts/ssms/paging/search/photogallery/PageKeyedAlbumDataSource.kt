package com.sts.ssms.paging.search.photogallery

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class PageKeyedAlbumDataSource(
    private val query: String,
    private val galleryRepository: GalleryRepository
) : PageKeyedItemDataSource<AlbumUiModel>() {
    override suspend fun fetchItem(page: Int): SearchResult<AlbumUiModel> =
        galleryRepository.getItem(page, query, 0)

    override fun getSearchRepository(): SearchRepository<AlbumUiModel> = galleryRepository
}