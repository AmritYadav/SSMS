package com.sts.ssms.ui.helpdesk.vendor

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.helpdesk.vendor.api.RatingAndComment
import com.sts.ssms.databinding.FragmentVendorDetailsBinding
import com.sts.ssms.ui.helpdesk.vendor.adapter.VendorStaffRatingsAdapter
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import com.sts.ssms.ui.helpdesk.vendor.viewmodel.VendorDetailsViewModel
import com.sts.ssms.utils.gone
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.visible
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.fragment_vendor_details.*
import kotlinx.android.synthetic.main.layout_vendor_details_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class VendorDetailsFragment : Fragment() {

    private val vendorDetailsViewModel by viewModel<VendorDetailsViewModel>()

    private lateinit var binding: FragmentVendorDetailsBinding

    private lateinit var fm: FragmentManager

    private lateinit var vendorStaffRatingsAdapter: VendorStaffRatingsAdapter
    private var vendorUiModel: VendorUiModel? = null

    companion object {
        const val KEY_VENDOR_UI_MODEL = "VENDOR_UI_MODEL"
        fun newInstance(bundle: Bundle): VendorDetailsFragment {
            return VendorDetailsFragment().apply {
                arguments = bundle
            }
        }
    }

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Prevent user interaction with the Android Back Button.
            // Allowing only Up Indicator to navigate back to the source screen
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        fm = childFragmentManager
        activity?.onBackPressedDispatcher?.addCallback(backPressedCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVendorDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vendorUiModel = arguments?.getParcelable(KEY_VENDOR_UI_MODEL)
        vendorUiModel?.let {
            binding.vendorUiModel = it
            vendorDetailsViewModel.vendorDetails(it.id)
            setObservable()
            setupRecycler(it.ratingsAndComments)
        }
        binding.btnVendorAddRatingComment.setOnClickListener {
            vendorUiModel?.let {
                VendorRatingsFragment.newInstance(it.id).apply {
                    show(fm, VendorRatingsFragment::javaClass.name)
                }
            }
        }
    }

    private fun setupRecycler(ratingList: List<RatingAndComment>) {
        tv_no_ratings.visibleGone(ratingList.isEmpty())
        vendorStaffRatingsAdapter = VendorStaffRatingsAdapter(ratingList)
        binding.rvVendorRatingsComments.apply {
            adapter = vendorStaffRatingsAdapter
        }
    }

    private fun setObservable() {
        vendorDetailsViewModel.vendorRatingResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                vendorUiModel?.let { vendor -> vendorDetailsViewModel.vendorDetails(vendor.id) }
                updateUserView(it.success)
            }
        }
        vendorDetailsViewModel.networkState.observe(viewLifecycleOwner) { networkState ->
            when (networkState) {
                NetworkState.LOADING -> pb_vendor_details_loader.visible(activity?.window)
                else -> {
                    networkState.msg?.let { msg -> updateUserView(msg) }
                    pb_vendor_details_loader.gone(activity?.window)
                }
            }
        }
        vendorDetailsViewModel.vendorDetailsResult.observe(viewLifecycleOwner) { vendorModel ->
            rating_vendor_details_rating_val.rating = vendorModel.overallRating.toFloat()
            vendorStaffRatingsAdapter.updateList(vendorModel.ratingsAndComments)
            tv_no_ratings.visibleGone(vendorModel.ratingsAndComments.isEmpty())
        }
    }

    private fun updateUserView(message: String) {
        activity?.showToast(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }
}
