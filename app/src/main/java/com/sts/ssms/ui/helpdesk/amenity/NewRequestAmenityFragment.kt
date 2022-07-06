package com.sts.ssms.ui.helpdesk.amenity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.amenity.model.AmenityCategory
import com.sts.ssms.data.helpdesk.amenity.model.AmenityRequestBody
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.AmenityMyRequestViewModel
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.NewRequestAmenityViewModel
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.fragment_request_amenity.*
import kotlinx.android.synthetic.main.layout_request_amenity_content.*
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewRequestAmenityFragment : BottomSheetDialogFragment() {

    private val viewModel by viewModel<NewRequestAmenityViewModel>()
    private val amenityMainViewModel by lazy { requireParentFragment().getViewModel<AmenityMyRequestViewModel>() }

    private var startDate = LocalDate.now()
    private var startTime: LocalTime = LocalTime.now()
    private var endDate = LocalDate.now()
    private var endTime: LocalTime = LocalTime.now().plusMinutes(30)

    private var amenityId: Int = 0
    private var subAmenityId: Int = 0
    private var itemId: Int = 0
    private var monthlyCharge: Int = 0
    private var amenityType: String = ""
    private lateinit var amenityCategories: List<AmenityCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAmenityCategory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_amenity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()

        start_date.setOnClickListener {
            val context = activity ?: return@setOnClickListener
            (context as AppCompatActivity).showDatePickerDialog(startDate) { date ->
                setStartDate(date)
                if (startDate > endDate)
                    setEndDate(date)
                viewModel.bookingDateFormChange(startDate, endDate, startTime, endTime)
            }
        }

        start_time.setOnClickListener {
            val context = activity ?: return@setOnClickListener
            context.showTimePickerDialog(startTime) { time ->
                setStartTime(time)
                viewModel.bookingDateFormChange(startDate, endDate, startTime, endTime)
            }
        }

        end_date.setOnClickListener {
            val context = activity ?: return@setOnClickListener
            (context as AppCompatActivity).showDatePickerDialog(endDate) { date ->
                setEndDate(date)
                viewModel.bookingDateFormChange(startDate, endDate, startTime, endTime)
            }
        }

        end_time.setOnClickListener {
            val context = activity ?: return@setOnClickListener
            context.showTimePickerDialog(endTime) { time ->
                setEndTime(time)
                viewModel.bookingDateFormChange(startDate, endDate, startTime, endTime)
            }
        }

        amenity_category.setOnClickListener {
            it.showAmenityCategoryPopupWindow(amenityCategories) { category ->
                amenityId = category.amenityId
                subAmenityId = category.subAmenityId
                amenityType = category.amenityType
                itemId = category.itemId
                monthlyCharge = category.monthlyCharge
                if (category.amenityType == "shareable") {
                    updateUiAmenity(true)
                } else
                    updateUiAmenity(false)
                viewModel.bookingDateFormChange(startDate, endDate, startTime, endTime)
            }
        }

        save_amenity.setOnClickListener {
            val bookingFrom = "${startDate.toStringEventDate1()} ${startTime.toStringEventTime()}"
            val bookingTo = "${endDate.toStringEventDate1()} ${endTime.toStringEventTime()}"
            val request =
                AmenityRequestBody(
                    amenityId,
                    subAmenityId,
                    amenityType,
                    itemId,
                    monthlyCharge,
                    bookingFrom,
                    bookingTo
                )
            viewModel.saveAmenityRequest(request)
        }
    }

    private fun updateUiAmenity(isShareable: Boolean) {
        if (isShareable)
            monthly_charges.text = monthlyCharge.toString()
        else
            initBookingDate()
        monthly_charges_detail.isVisible = isShareable
        booking_date_details.isVisible = !isShareable
    }

    private fun setupObserver() {
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> progress_amenity_category.visible(activity?.window)
                else -> {
                    progress_amenity_category.gone(activity?.window)
                    if (!it.msg.isNullOrEmpty()) updateUserView(it.msg)
                }
            }
        }
        viewModel.bookingDateStatus.observe(viewLifecycleOwner) {
            if (it.startDateError != null) {
                start_date.setTextColor(Color.RED)
            } else{
                start_date.setTextColor(getColor(requireContext(), R.color.content))
            }
            if (it.startTimeError != null) start_time.setTextColor(Color.RED)
            save_amenity.isEnabled = it.isDataValid && amenityId > 0
            if (it.isDataValid) {
                view?.let { v ->
                    start_date.setTextColor(getColor(v.context, R.color.content))
                    start_time.setTextColor(getColor(v.context, R.color.content))
                }
            }
        }
        viewModel.categoryResult.observe(viewLifecycleOwner) {
            amenityCategories = it
        }
        viewModel.newAmenityRequestResult.observe(viewLifecycleOwner) {
            if (!it.success.isNullOrEmpty()) {
                amenityMainViewModel.loadList.value = true
                updateUserView(it.success)
                dismiss()
            }
            if (!it.error.isNullOrEmpty()) updateUserView(it.error)
        }
    }

    private fun initBookingDate() {
        start_date.text = startDate.toStringEventDate()
        start_time.text = startTime.toStringEventTime()
        end_date.text = endDate.toStringEventDate()
        end_time.text = endTime.toStringEventTime()
    }

    private fun setStartDate(date: LocalDate) {
        startDate = date
        start_date.text = date.toStringEventDate()
    }

    private fun setStartTime(time: LocalTime) {
        startTime = time
        start_time.text = time.toStringEventTime()
    }

    private fun setEndDate(date: LocalDate) {
        endDate = date
        end_date.text = date.toStringEventDate()
    }

    private fun setEndTime(time: LocalTime) {
        endTime = time
        end_time.text = time.toStringEventTime()
    }

    private fun updateUserView(msg: String) = activity?.showToast(msg)
}