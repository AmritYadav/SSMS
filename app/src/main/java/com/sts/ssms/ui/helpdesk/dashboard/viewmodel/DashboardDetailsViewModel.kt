package com.sts.ssms.ui.helpdesk.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.data.helpdesk.dashboard.repository.DashboardRepository
import com.sts.ssms.ui.helpdesk.dashboard.model.Comment
import com.sts.ssms.ui.helpdesk.dashboard.model.CommentResult
import kotlinx.coroutines.*

class DashboardDetailsViewModel(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _commentListResult = MutableLiveData<List<Comment>>()
    val commentListResult: LiveData<List<Comment>> = _commentListResult

    private var _commentResult = MutableLiveData<CommentResult>()
    val commentResult: LiveData<CommentResult> = _commentResult

    private var _commentForm = MutableLiveData<Boolean>()
    val commentForm: LiveData<Boolean> = _commentForm

    fun commentFormDataChange(comment: String) {
        _commentForm.value = isCommentValid(comment)
    }

    fun commentsList(ticketId: Int) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = dashboardRepository.commentsList(ticketId)
            withContext(Dispatchers.Main) {
                delay(150)
                if (result.comments != null) {
                    _commentListResult.value = result.comments
                    _networkState.value = NetworkState.LOADED
                } else {
                    _networkState.value = NetworkState.error(result.error)
                }
            }
        }
    }

    fun comment(ticketId: Int, comment: String) {
        coroutineScope.launch {
            withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
            val result = dashboardRepository.commentOnTicket(ticketId, comment)
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

    private fun isCommentValid(comment: String): Boolean = comment.length > 5

    override fun onCleared() {
        super.onCleared()
        completableJob.cancel()
    }
}