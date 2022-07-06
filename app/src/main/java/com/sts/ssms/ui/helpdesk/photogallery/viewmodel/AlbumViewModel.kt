package com.sts.ssms.ui.helpdesk.photogallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.photogallery.repository.GalleryRepository
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import kotlinx.coroutines.*

class AlbumViewModel(
    private val galleryRepository: GalleryRepository)
    : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val _albumResult = MutableLiveData<AlbumUiModel>()
    val albumResult: LiveData<AlbumUiModel> = _albumResult

    fun loadAlbum(albumId: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = galleryRepository.albumPhotos(albumId)
            withContext(Dispatchers.Main) {
                if (result.albumUiModel != null) {
                    _networkState.value = NetworkState.LOADED
                    _albumResult.value = result.albumUiModel
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

}