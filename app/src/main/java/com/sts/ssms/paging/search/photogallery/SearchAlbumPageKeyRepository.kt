package com.sts.ssms.paging.search.photogallery

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class SearchAlbumPageKeyRepository(private val galleryRepository: GalleryRepository) :
    PageKeyRepository<AlbumUiModel>() {
    override fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<AlbumUiModel> =
        SearchAlbumDataSourceFactory(query, galleryRepository)
}