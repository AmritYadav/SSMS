package com.sts.ssms.ui.helpdesk.staff.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult
import kotlinx.coroutines.*

class StaffDetailsViewModel(private val staffRepository: StaffRepository) : ViewModel() {

    private val job = Dispatchers.IO + SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    val staffRatingResult = MutableLiveData<VendorStaffRatingResult>()

    private var _staffDetailsResult = MutableLiveData<StaffUiModel>()
    val staffDetailsResult: LiveData<StaffUiModel> = _staffDetailsResult

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    var documentUrl: String? = null

    fun staffDetails(staffId: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = staffRepository.staff(staffId)
            withContext(Dispatchers.Main) {
                if (result.staffDetails != null) {
                    _staffDetailsResult.value = result.staffDetails
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
        documentUrl = null
    }

}