package com.sts.ssms.ui.helpdesk.staff

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffDetailsViewModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffRatingViewModel
import com.sts.ssms.utils.afterTextChanged
import com.sts.ssms.utils.formatString
import kotlinx.android.synthetic.main.dialog_fragment_vendor_staff_rating.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StaffRatingsFragment : DialogFragment() {

    private val ratingViewModel by viewModel<StaffRatingViewModel>()
    private val staffDetailsViewModel by lazy { requireParentFragment().getViewModel<StaffDetailsViewModel>() }

    companion object {
        private const val KEY_STAFF_ID = "STAFF_ID"
        fun newInstance(vendorId: Int): StaffRatingsFragment {
            return StaffRatingsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_STAFF_ID, vendorId)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_vendor_staff_rating, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_vendor_staff_rate_label.formatString(R.string.prompt_vendor_rating)
        val staffId = arguments?.getInt(KEY_STAFF_ID) ?: -1

        ratingViewModel.staffRatingFormStatus.observe(viewLifecycleOwner) {

            btn_vendor_staff_rating_submit.isEnabled = it.isDataValid

            if (it.commentError != null) til_vendor_staff_rating_comments.error =
                getString(it.commentError)
            else til_vendor_staff_rating_comments.error = null

//            if (it.ratingError != null) activity?.showToast(getString(it.ratingError))

        }

        ratingViewModel.staffRatingResult.observe(viewLifecycleOwner) {
            staffDetailsViewModel.staffRatingResult.value = it
            dialog?.dismiss()
        }

        ti_et_vendor_staff_rating_comments.afterTextChanged {
            ratingViewModel.staffRatingForm(
                ti_et_vendor_staff_rating_comments.text.toString(),
                rating_vendor_staff.rating.toInt()
            )
        }

        rating_vendor_staff.setOnRatingBarChangeListener { _, rating, _ ->
            ratingViewModel.staffRatingForm(
                ti_et_vendor_staff_rating_comments.text.toString(),
                rating.toInt()
            )
        }

        btn_vendor_staff_rating_cancel.setOnClickListener {
            dialog?.dismiss()
        }

        btn_vendor_staff_rating_submit.setOnClickListener {
            ratingViewModel.staffRatingComment(
                staffId,
                ti_et_vendor_staff_rating_comments.text.toString(),
                rating_vendor_staff.rating.toInt()
            )
        }
    }
}
