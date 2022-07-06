package com.sts.ssms.ui.helpdesk.societyevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.societyevent.adapter.SocietyEventAdapter
import com.sts.ssms.ui.helpdesk.societyevent.viewmodel.SocietyEventViewModel
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.fragment_society_event.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val MY_SOCIETY_EVENT = "My Request"
const val ALL_SOCIETY_EVENT = "All Request"

class SocietyEventFragment : Fragment() {

    private val societyEventViewModel by viewModel<SocietyEventViewModel>()
    private lateinit var societyEventAdapter: SocietyEventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_society_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecycler()
        setupObservables()
        configureSwipeRefresh()

        fab_society_event.setOnClickListener {
            SuggestActivityFragment().show(childFragmentManager, "SocietyEvent")
        }
    }

    private fun configureRecycler() {
        societyEventAdapter = SocietyEventAdapter { societyEventViewModel.retry() }
        rv_society_event.apply { adapter = societyEventAdapter }
    }

    private fun setupObservables() {
        societyEventViewModel.networkState?.observe(viewLifecycleOwner) { it ->
            societyEventAdapter.updateNetworkState(it)
            societyEventViewModel.societyEventList.value?.let {
                view_empty_state.updateEmptyState(it.isEmpty(), getString(R.string.prompt_empty_society_event))
            }
        }
        societyEventViewModel.societyEventList.observe(viewLifecycleOwner) {
            societyEventAdapter.submitList(it)
        }
    }

    private fun configureSwipeRefresh() {
        var isSwipeEnabled = false
        swipe_refresh_society.apply {
            // Observing the NetworkState, to load fresh data or show retry button
            societyEventViewModel.refreshState?.observe(viewLifecycleOwner) {
                if (isSwipeEnabled) {
                    isRefreshing = it == NetworkState.LOADING
                    isSwipeEnabled = isRefreshing
                }
            }

            setOnRefreshListener {
                isSwipeEnabled = true
                societyEventViewModel.refreshAllData()
            }
        }
    }
}