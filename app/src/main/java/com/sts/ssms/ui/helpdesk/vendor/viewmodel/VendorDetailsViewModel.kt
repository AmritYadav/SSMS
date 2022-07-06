package com.sts.ssms.ui.helpdesk.vendor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import kotlinx.coroutines.*

class VendorDetailsViewModel(private val vendorRepository: VendorRepository) : ViewModel() {

    private val job = Dispatchers.IO + SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    val vendorRatingResult = MutableLiveData<VendorStaffRatingResult>()

    private var _vendorDetailsResult = MutableLiveData<VendorUiModel>()
    val vendorDetailsResult: LiveData<VendorUiModel> = _vendorDetailsResult

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    fun vendorDetails(vendorId: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = vendorRepository.vendor(vendorId)
            withContext(Dispatchers.Main) {
                if (result.vendorDetails != null) {
                    _vendorDetailsResult.value = result.vendorDetails
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}