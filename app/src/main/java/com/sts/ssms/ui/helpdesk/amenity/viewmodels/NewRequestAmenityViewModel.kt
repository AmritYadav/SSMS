package com.sts.ssms.ui.helpdesk.amenity.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.amenity.model.AmenityCategory
import com.sts.ssms.data.helpdesk.amenity.model.AmenityRequestBody
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.AmenityRequestResult
import com.sts.ssms.ui.helpdesk.amenity.model.BookingDateStatus
import kotlinx.coroutines.*
import org.joda.time.LocalDate
import org.joda.time.LocalTime

class NewRequestAmenityViewModel(private val amenityRepository: AmenityRepository) : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    private var _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private var _categoryResult = MutableLiveData<List<AmenityCategory>>()
    val categoryResult: LiveData<List<AmenityCategory>> = _categoryResult

    private var _bookingDateStatus = MutableLiveData<BookingDateStatus>()
    val bookingDateStatus: LiveData<BookingDateStatus> = _bookingDateStatus

    private var _newAmenityRequestResult = MutableLiveData<AmenityRequestResult>()
    val newAmenityRequestResult: LiveData<AmenityRequestResult> = _newAmenityRequestResult

    fun loadAmenityCategory() = coroutineScope.launch {
        withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
        val response = amenityRepository.amenityCategories()
        withContext(Dispatchers.Main) {
            if (!response.categories.isNullOrEmpty()) {
                _categoryResult.value = response.categories
                _networkState.value = NetworkState.LOADED
            } else {
                _networkState.value = NetworkState.error(response.error)
            }
        }
    }

    fun saveAmenityRequest(requestBody: AmenityRequestBody) = coroutineScope.launch {
        withContext(Dispatchers.Main) { _networkState.value = NetworkState.LOADING }
        val response = amenityRepository.saveAmenityRequest(requestBody)
        withContext(Dispatchers.Main) {
            _newAmenityRequestResult.value = response
            _networkState.value = NetworkState.LOADED
        }
    }

    fun bookingDateFormChange(
        startDate: LocalDate, endDate: LocalDate,
        startTime: LocalTime, endTime: LocalTime
    ) {
        if (!isDateValid(startDate, endDate)) {
            _bookingDateStatus.value =
                BookingDateStatus(startDateError = R.string.booking_start_date_error)
        } else if (!isStartTimeValid(startDate, endDate, startTime, endTime)) {
            _bookingDateStatus.value =
                BookingDateStatus(startTimeError = R.string.booking_start_time_error)
        } else {
            _bookingDateStatus.value = BookingDateStatus(isDataValid = true)
        }
    }

    private fun isDateValid(startDate: LocalDate, endDate: LocalDate) = startDate <= endDate
    private fun isStartTimeValid(
        startDate: LocalDate, endDate: LocalDate,
        startTime: LocalTime, endTime: LocalTime
    ) =
        when {
            startDate == endDate -> startTime < endTime
            startDate < endDate -> true
            else -> false
        }

}