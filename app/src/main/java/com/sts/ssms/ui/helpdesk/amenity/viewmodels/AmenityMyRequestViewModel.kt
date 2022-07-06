package com.sts.ssms.ui.helpdesk.amenity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.MyAmenityResult
import kotlinx.coroutines.*

class AmenityMyRequestViewModel(private val amenityRepository: AmenityRepository) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _myRequestResult = MutableLiveData<MyAmenityResult>()
    val requestResult: LiveData<MyAmenityResult> = _myRequestResult

    var loadList = MutableLiveData<Boolean>()

    fun loadMyRequestedAmenities() {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = amenityRepository.myAmenities()
            withContext(Dispatchers.Main) {
                _myRequestResult.value = result
                _networkState.value = NetworkState.LOADED
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}