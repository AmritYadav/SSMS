package com.sts.ssms.ui.helpdesk.vendor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.data.helpdesk.vendor.api.VendorRatingRequest
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingFormStatus
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult
import kotlinx.coroutines.*

class VendorRatingViewModel(private val vendorRepository: VendorRepository) : ViewModel() {

    private val job = Dispatchers.IO + SupervisorJob()
    private val coroutineScope = CoroutineScope(job)

    private var _ratingFormState = MutableLiveData<VendorStaffRatingFormStatus>()
    val staffRatingFormStatus: LiveData<VendorStaffRatingFormStatus> = _ratingFormState

    private var _vendorRatingResult = MutableLiveData<VendorStaffRatingResult>()
    val vendorStaffRatingResult: LiveData<VendorStaffRatingResult> = _vendorRatingResult

    fun vendorRatingForm(comment: String, rating: Int) {
        if (!isCommentValid(comment))
            _ratingFormState.value = VendorStaffRatingFormStatus(commentError = R.string.invalid_comment)
        else if (!isRatingValid(rating))
            _ratingFormState.value = VendorStaffRatingFormStatus(ratingError = R.string.invalid_rating)
        else
            _ratingFormState.value = VendorStaffRatingFormStatus(isDataValid = true)
    }

    fun vendorRatingComment(vendorId: Int, comment: String, rating: Int) {
        coroutineScope.launch {
            val result =
                vendorRepository.vendorRatings(
                    VendorRatingRequest(vendorId, rating, comment)
                )
            withContext(Dispatchers.Main) {
                _vendorRatingResult.value = result
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