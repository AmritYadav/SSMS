package com.sts.ssms.ui.helpdesk.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.photogallery.PhotoGalleryDataSourceFactory
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

private const val PAGE_SIZE = 6

class PhotoGalleryViewModel(private val photoGalleryDataSourceFactory: PhotoGalleryDataSourceFactory):
    ViewModel() {
    var albumList: LiveData<PagedList<AlbumUiModel>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        albumList = LivePagedListBuilder(photoGalleryDataSourceFactory, config).build()
        networkState =
            Transformations.switchMap(photoGalleryDataSourceFactory.photoGalleryDataSourceLiveData) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(photoGalleryDataSourceFactory.photoGalleryDataSourceLiveData  ) { it.refreshState() }

    }


    fun retry() = photoGalleryDataSourceFactory.photoGalleryDataSourceLiveData.value?.retry()

    fun refreshAllData() = photoGalleryDataSourceFactory.photoGalleryDataSourceLiveData.value?.refresh()


    override fun onCleared() {
        super.onCleared()
        photoGalleryDataSourceFactory.photoGalleryDataSourceLiveData.value?.onCleared()
    }
}