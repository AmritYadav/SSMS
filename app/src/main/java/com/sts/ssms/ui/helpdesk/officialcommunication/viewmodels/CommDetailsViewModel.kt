package com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.communication.repository.CommRepository
import com.sts.ssms.ui.helpdesk.dashboard.model.CommentResult
import com.sts.ssms.ui.helpdesk.officialcommunication.models.CommunicationResult
import kotlinx.coroutines.*

class CommDetailsViewModel(private val commRepository: CommRepository) : ViewModel() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _communicationResult = MutableLiveData<CommunicationResult>()
    val communicationsResult: LiveData<CommunicationResult> = _communicationResult

    private var _commentForm = MutableLiveData<Boolean>()
    val commentForm: LiveData<Boolean> = _commentForm

    private var _commentResult = MutableLiveData<CommentResult>()
    val commentResult: LiveData<CommentResult> = _commentResult

    fun commentFormDataChange(comment: String) {
        _commentForm.value = isCommentValid(comment)
    }

    fun communication(commId: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = commRepository.communication(commId)
            withContext(Dispatchers.Main) {
                delay(150)
                if (result.communication != null) {
                    _communicationResult.value = result
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    fun comment(letterId: Int, comment: String) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = commRepository.commentOnLetter(letterId, comment)
            withContext(Dispatchers.Main) {
                if (result.success != null) {
                    _commentResult.value = result
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    private fun isCommentValid(comment: String): Boolean = comment.length > 1
}