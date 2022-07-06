package com.sts.ssms.ui.search.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.paging.search.photogallery.SearchAlbumPageKeyRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class SearchAlbumViewModel(private val galleryRepository: GalleryRepository) :
    SearchViewModel<AlbumUiModel>() {

    override val repoResult: LiveData<Listing<AlbumUiModel>> = Transformations.map(query) {
        SearchAlbumPageKeyRepository(galleryRepository).getItems(it, filterId)
    }

}