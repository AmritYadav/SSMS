package com.sts.ssms.paging.search.photogallery

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class SearchAlbumDataSourceFactory(
    private val query: String,
    private val galleryRepository: GalleryRepository
) : ItemDataSourceFactory<AlbumUiModel>() {
    override fun getDataSource(): PageKeyedItemDataSource<AlbumUiModel> =
        PageKeyedAlbumDataSource(query, galleryRepository)
}