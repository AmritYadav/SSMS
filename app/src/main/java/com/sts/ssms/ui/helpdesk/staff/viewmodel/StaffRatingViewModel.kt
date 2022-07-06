package com.sts.ssms.ui.helpdesk.staff.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.staff.api.StaffRatingRequest
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingFormStatus
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult
import kotlinx.coroutines.*

class StaffRatingViewModel(private val staffRepository: StaffRepository) : ViewModel() {

    private val job = Dispatchers.IO + SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    private var _ratingFormState = MutableLiveData<VendorStaffRatingFormStatus>()
    val staffRatingFormStatus: LiveData<VendorStaffRatingFormStatus> = _ratingFormState

    private var _staffRatingResult = MutableLiveData<VendorStaffRatingResult>()
    val staffRatingResult: LiveData<VendorStaffRatingResult> = _staffRatingResult

    fun staffRatingForm(comment: String, rating: Int) {
        if (!isCommentValid(comment))
            _ratingFormState.value =
                VendorStaffRatingFormStatus(commentError = R.string.invalid_comment)
        else if (!isRatingValid(rating))
            _ratingFormState.value =
                VendorStaffRatingFormStatus(ratingError = R.string.invalid_rating)
        else
            _ratingFormState.value = VendorStaffRatingFormStatus(isDataValid = true)
    }

    fun staffRatingComment(staffId: Int, comment: String, rating: Int) {
        coroutineScope.launch {
            val result =
                staffRepository.staffRatings(
                    StaffRatingRequest(staffId, rating, comment)
                )
            withContext(Dispatchers.Main) {
                _staffRatingResult.value = result
            }
        }
    }

    private fun isCommentValid(comment: String): Boolean {
        return comment.length > 1
    }

    private fun isRatingValid(rating: Int): Boolean {
        return rating > 0
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}