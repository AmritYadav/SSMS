package com.sts.ssms.paging.photogallery

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel

class PhotoGalleryDataSourceFactory(private val photoGalleryRepository: GalleryRepository): DataSource.Factory<Int, AlbumUiModel>() {

    val photoGalleryDataSourceLiveData = MutableLiveData<PhotoGalleryDataSource>()

    override fun create(): DataSource<Int, AlbumUiModel> {
        val photoGalleryDataSource = PhotoGalleryDataSource(photoGalleryRepository)
        photoGalleryDataSourceLiveData.postValue(photoGalleryDataSource)
        return photoGalleryDataSource
    }


}