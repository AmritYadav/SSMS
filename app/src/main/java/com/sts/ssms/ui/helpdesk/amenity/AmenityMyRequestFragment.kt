package com.sts.ssms.ui.helpdesk.amenity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.amenity.adapters.MyRequestedAmenityAdapter
import com.sts.ssms.ui.helpdesk.amenity.model.MyAmenityResult
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.AmenityMyRequestViewModel
import com.sts.ssms.utils.showToast
import com.sts.ssms.utils.updateEmptyState
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.fragment_amenity_my_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AmenityMyRequestFragment : Fragment() {

    private val viewModel by viewModel<AmenityMyRequestViewModel>()

    private lateinit var myRequestAdapter: MyRequestedAmenityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadMyRequestedAmenities()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_amenity_my_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()

        request_amenity.setOnClickListener {
            NewRequestAmenityFragment().show(
                childFragmentManager.beginTransaction(),
                NewRequestAmenityFragment::javaClass.name
            )
        }
    }

    private fun setupObservers() {
        viewModel.networkState.observe(viewLifecycleOwner) { it ->
            updateViewWithProgress(it)
            viewModel.requestResult.value?.let {
                it.myAmenities?.let { list ->
                    view_empty_state.updateEmptyState(
                        list.isEmpty(),
                        getString(R.string.prompt_empty_state_amenity)
                    )
                }
            }
        }

        viewModel.requestResult.observe(viewLifecycleOwner) {
            handleResult(it)
        }
        viewModel.loadList.observe(viewLifecycleOwner) {
            viewModel.loadMyRequestedAmenities()
        }
    }

    private fun setupRecyclerView() {
        myRequestAdapter = MyRequestedAmenityAdapter()
        rv_my_request.apply {
            adapter = myRequestAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) request_amenity.hide()
                    if (dy < 0) request_amenity.show()
                }
            })
        }
    }

    private fun handleResult(requestResult: MyAmenityResult) {
        if (requestResult.error != null) {
            updateUserView(requestResult.error)
            return
        }
        if (requestResult.myAmenities != null) {
            myRequestAdapter.submitList(requestResult.myAmenities)
            rv_my_request.postDelayed({
                rv_my_request.layoutManager?.scrollToPosition(0)
            }, 200)
        }
    }

    private fun updateViewWithProgress(networkState: NetworkState) {
        when (networkState) {
            NetworkState.LOADING -> progress_my_request.visibleGone(true)
            NetworkState.LOADED -> progress_my_request.visibleGone(false)
        }
    }

    private fun updateUserView(msg: String) = activity?.showToast(msg)
}
