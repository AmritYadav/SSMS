package com.sts.ssms.ui.helpdesk.amenity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.helpdesk.amenity.adapters.AllAmenityRequestAdapter
import com.sts.ssms.ui.helpdesk.amenity.viewmodels.AmenityAllRequestViewModel
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_amenity_all_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AmenityAllRequestFragment : Fragment() {

    private val amenityViewModel by viewModel<AmenityAllRequestViewModel>()
    private lateinit var allAmenityReqAdapter: AllAmenityRequestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_amenity_all_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        configureRecycler()
        configureSwipeRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                val searchMenuView: View =
                    (activity as HelpDeskActivity).toolbar.findViewById(R.id.action_search)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    searchMenuView, getString(R.string.transition_search_back)
                ).toBundle()

                val intent = Intent(activity, SearchActivity::class.java).apply {
                    action = Intent.ACTION_SEARCH
                    putExtras(Bundle().apply {
                        putParcelable(KEY_NAV_TYPE, NavType.ALL_AMENITY_REQUEST)
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservables() {
        amenityViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            allAmenityReqAdapter.updateNetworkState(it)
            amenityViewModel.allAmenityRequests.value?.let {
                view_empty_state.updateEmptyState(
                    it.isEmpty(),
                    getString(R.string.prompt_empty_state_amenity)
                )
            }
        }
        amenityViewModel.allAmenityRequests.observe(viewLifecycleOwner) {
            allAmenityReqAdapter.submitList(it)
        }
    }

    private fun configureRecycler() {
        allAmenityReqAdapter = AllAmenityRequestAdapter { amenityViewModel.retry() }
        rv_all_amenity_req.apply {
            adapter = allAmenityReqAdapter
        }
    }

    private fun configureSwipeRefresh() {
        swipe_refresh.apply {
            amenityViewModel.refreshState?.observe(viewLifecycleOwner) {
                isRefreshing = it == NetworkState.LOADING
            }
            setOnRefreshListener {
                amenityViewModel.refreshAllData()
            }
        }
    }
}
