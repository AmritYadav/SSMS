package com.sts.ssms.ui.helpdesk.conversations.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.conversations.repository.PostRepository
import com.sts.ssms.ui.helpdesk.conversations.model.newpost.NewPostFormState
import com.sts.ssms.ui.helpdesk.conversations.model.newpost.NewPostResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewPostViewModel(private val postRepository: PostRepository) : ViewModel() {

    private var _newPostFormState = MutableLiveData<NewPostFormState>()
    val newPostFormState: LiveData<NewPostFormState> = _newPostFormState

    private val _postResult = MutableLiveData<NewPostResult>()
    val postResult: LiveData<NewPostResult> = _postResult

    fun newPostFormState(title: String, description: String) {
        if (!isValid(title))
            _newPostFormState.value =
                NewPostFormState(
                    titleError = R.string.invalid_new_post_title
                )
        else if (!isValid(description))
            _newPostFormState.value =
                NewPostFormState(
                    descriptionError = R.string.invalid_new_post_desc
                )
        else _newPostFormState.value =
            NewPostFormState(
                isDataValid = true
            )
    }

    fun postNewConversation(title: String, description: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val postResult = postRepository.postConversation(title, description)
            withContext(Dispatchers.Main) {
                _postResult.value = postResult
            }
        }
    }

    private fun isValid(title: String): Boolean {
        return title.trim().length > 4
    }
}