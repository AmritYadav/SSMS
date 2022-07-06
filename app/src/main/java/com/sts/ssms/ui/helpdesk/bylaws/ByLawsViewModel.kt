package com.sts.ssms.ui.helpdesk.bylaws

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.bylaws.repository.ByLawsRepository
import com.sts.ssms.ui.helpdesk.bylaws.model.ByLawFormDownloadResult
import kotlinx.coroutines.*

class ByLawsViewModel(private val byLawsRepository: ByLawsRepository) : ViewModel() {
    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _documentDownloadResult = MutableLiveData<ByLawFormDownloadResult>()
    val documentDownloadResult: LiveData<ByLawFormDownloadResult> = _documentDownloadResult

    private var _downloadStatus = MutableLiveData<NetworkState>()
    val downloadState: LiveData<NetworkState>
        get() = _downloadStatus

    fun byLawsForm(formNumber: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _downloadStatus.value = NetworkState.LOADING }
            val downloadResult = byLawsRepository.getDocument(formNumber)
            withContext(Dispatchers.Main) { _documentDownloadResult.value = downloadResult }
            withContext(Dispatchers.Main) { _downloadStatus.value = NetworkState.LOADED }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}