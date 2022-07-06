package com.sts.ssms.ui.helpdesk.documents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import kotlinx.coroutines.*

class DocumentDetailViewModel(private val documentsRepository: DocumentsRepository):ViewModel() {

    private val job = Dispatchers.IO + SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    private var _documentDetailResult = MutableLiveData<Documents>()
    val documentDetailResult: LiveData<Documents> = _documentDetailResult

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}