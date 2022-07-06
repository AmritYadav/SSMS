package com.sts.ssms.ui.helpdesk.societyevent.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.data.helpdesk.societyevent.repository.SocietyEventRepository
import com.sts.ssms.ui.helpdesk.societyevent.model.EventPostResult
import com.sts.ssms.ui.helpdesk.societyevent.model.SuggestEventFormState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SuggestActivityViewModel(private val societyEventRepository: SocietyEventRepository) :
    ViewModel() {

    private var _newPostFormState = MutableLiveData<SuggestEventFormState>()
    val newPostFormState: LiveData<SuggestEventFormState> = _newPostFormState

    private val _postResult = MutableLiveData<EventPostResult>()
    val postResult: LiveData<EventPostResult> = _postResult

    fun newPostFormState(eventTitle: String) {
        if (isTitleValid(eventTitle))
            _newPostFormState.value = SuggestEventFormState(
                isDataValid = true
            )
    }

    fun postNewConversation(eventTitle: String, date: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val postResult = societyEventRepository.postSuggestEvent(eventTitle, date)
            withContext(Dispatchers.Main) {
                _postResult.value = postResult
            }
        }
    }

    private fun isTitleValid(title: String): Boolean {
        return title.trim().length > 5
    }
}