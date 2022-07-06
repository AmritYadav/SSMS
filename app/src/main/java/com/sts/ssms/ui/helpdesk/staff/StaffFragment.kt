package com.sts.ssms.ui.helpdesk.staff

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.helpdesk.staff.adapter.StaffAdapter
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import com.sts.ssms.ui.helpdesk.staff.viewmodel.StaffViewModel
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_vendor_staff.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class StaffFragment : Fragment() {

    private val viewModel by viewModel<StaffViewModel>()

    private lateinit var staffAdapter: StaffAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_vendor_staff, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setupObservables()
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
                        putParcelable(KEY_NAV_TYPE, NavType.STAFF)
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservables() {
        viewModel.networkState?.observe(viewLifecycleOwner) { it ->
            staffAdapter.updateNetworkState(it)
            viewModel.staffList.value?.let {
                view_empty_state.updateEmptyState(it.isEmpty(), getString(R.string.prompt_empty_staff))
            }
        }
        viewModel.staffList.observe(viewLifecycleOwner) {
            staffAdapter.submitList(it)
            /*helpDeskViewModel.reloadList.value?.let { reloadList ->
                if (reloadList) {
                    viewModel.refreshAllData()
                    helpDeskViewModel.reloadList.value = false
                }
            }*/
        }
    }

    private fun configureRecycler() {
        staffAdapter =
            StaffAdapter({ viewModel.retry() }, { staff -> navigateToStaffDetails(staff) })
        rv_vendor_staff.apply {
            adapter = staffAdapter
        }
    }

    private fun configureSwipeRefresh() {
        var isSwipeEnabled = false
        swipe_refresh_vendor_staff.apply {

            // Observing the NetworkState, to load fresh data or show retry button
            viewModel.refreshState?.observe(viewLifecycleOwner) {
                if (isSwipeEnabled) {
                    isRefreshing = it == NetworkState.LOADING
                    isSwipeEnabled = isRefreshing
                }
            }

            setOnRefreshListener {
                isSwipeEnabled = true
                viewModel.refreshAllData()
            }
        }
    }

    private fun navigateToStaffDetails(staff: StaffUiModel) {
        val bundle = Bundle().apply {
            putParcelable(StaffDetailsFragment.KEY_STAFF_UI_MODEL, staff)
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.nav_staff_details, bundle) }
    }
}
