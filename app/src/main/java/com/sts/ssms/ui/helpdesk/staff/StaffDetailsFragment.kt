package com.sts.ssms.ui.helpdesk.staff

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.databinding.FragmentStaffDetailsBinding
import com.sts.ssms.ui.helpdesk.staff.adapter.StaffDocumentAdapter
import com.sts.ssms.ui.helpdesk.staff.adapter.StaffWorkingAtAdapter
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffDetailsViewModel
import com.sts.ssms.ui.helpdesk.vendor.adapter.VendorStaffRatingsAdapter
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.fragment_staff_details.*
import kotlinx.android.synthetic.main.layout_staff_details_content.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StaffDetailsFragment : Fragment() {

    private val staffDetailsViewModel by viewModel<StaffDetailsViewModel>()

    private lateinit var binding: FragmentStaffDetailsBinding

    private lateinit var fm: FragmentManager

    private lateinit var vendorStaffRatingsAdapter: VendorStaffRatingsAdapter
    private lateinit var staffWorkingAtAdapter: StaffWorkingAtAdapter
    private lateinit var staffDocumentAdapter: StaffDocumentAdapter

    private var staffUiModel: StaffUiModel? = null

    companion object {
        const val KEY_STAFF_UI_MODEL = "STAFF_UI_MODEL"
        fun newInstance(bundle: Bundle): StaffDetailsFragment {
            return StaffDetailsFragment().apply {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStaffDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        staffUiModel = arguments?.getParcelable(KEY_STAFF_UI_MODEL)
        staffUiModel?.let {
            binding.staffUiModel = it
            staffDetailsViewModel.staffDetails(it.id)
            setObservable()
            setupRecycler(it)
        }
        binding.btnStaffAddRatingComment.setOnClickListener {
            staffUiModel?.let {
                StaffRatingsFragment.newInstance(it.id).apply {
                    show(fm, StaffRatingsFragment::javaClass.name)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_notification)?.let {
            it.isVisible = false
        }
    }

    private fun setObservable() {
        staffDetailsViewModel.staffRatingResult.observe(viewLifecycleOwner) {
            if (it.success != null) {
                staffUiModel?.let { staff -> staffDetailsViewModel.staffDetails(staff.id) }
                updateUserView(it.success)
            }
        }
        staffDetailsViewModel.networkState.observe(viewLifecycleOwner) { networkState ->
            when (networkState) {
                NetworkState.LOADING -> pb_staff_details_loader.visible(activity?.window)
                else -> {
                    networkState.msg?.let { msg -> updateUserView(msg) }
                    pb_staff_details_loader.gone(activity?.window)
                }
            }
        }
        staffDetailsViewModel.staffDetailsResult.observe(viewLifecycleOwner) { staffModel ->
            iv_staff_details_avatar.loadCircularImage(staffModel.imageUrl)
            updateAdapters(staffModel)
        }
    }

    private fun setupRecycler(staffUiModel: StaffUiModel) {

        hideShowEmptyStateMessages(staffUiModel)

        vendorStaffRatingsAdapter = VendorStaffRatingsAdapter(staffUiModel.ratingsAndComments)
        binding.rvStaffRatingsComments.apply {
            adapter = vendorStaffRatingsAdapter
        }

        staffWorkingAtAdapter = StaffWorkingAtAdapter(staffUiModel.workingAt)
        binding.rvStaffWorkingAt.apply {
            adapter = staffWorkingAtAdapter
        }

        staffDocumentAdapter = StaffDocumentAdapter(staffUiModel.documents)
        { downloadUrl ->
            staffDetailsViewModel.documentUrl = downloadUrl
            downloadUrl?.let {
                val context = activity ?: return@StaffDocumentAdapter
                context.checkStoragePermissionAndDownload(it)
            }
        }
        binding.rvStaffDocument.apply {
            adapter = staffDocumentAdapter
        }
    }

    private fun hideShowEmptyStateMessages(staffUiModel: StaffUiModel) {
        tv_no_documents.visibleGone(staffUiModel.documents.isEmpty())
        tv_no_ratings.visibleGone(staffUiModel.ratingsAndComments.isEmpty())
        tv_working_not_mentioned.visibleGone(staffUiModel.workingAt.isEmpty())
    }

    private fun updateUserView(message: String) {
        activity?.showToast(message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        backPressedCallback.remove()
    }

    fun onPermissionGranted() {
        staffDetailsViewModel.documentUrl?.let { activity?.downloadFile(it) }
    }

    private fun updateAdapters(staffUiModel: StaffUiModel) {
        vendorStaffRatingsAdapter.updateList(staffUiModel.ratingsAndComments)
        staffWorkingAtAdapter.updateList(staffUiModel.workingAt)
        staffDocumentAdapter.updateList(staffUiModel.documents)
        hideShowEmptyStateMessages(staffUiModel)
    }
}
