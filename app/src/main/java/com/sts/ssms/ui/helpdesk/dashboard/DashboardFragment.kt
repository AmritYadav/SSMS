package com.sts.ssms.ui.helpdesk.dashboard

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.helpdesk.dashboard.adapter.DashboardTicketAdapter
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.ui.helpdesk.dashboard.viewmodel.DashboardViewModel
import com.sts.ssms.ui.search.SearchTicketActivity
import com.sts.ssms.ui.search.ticket.KEY_FILTER_ACTION
import com.sts.ssms.ui.search.ticket.KEY_TICKETS
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_empty_state.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private val dashboardViewModel by viewModel<DashboardViewModel>()
    private lateinit var dashboardAdapter: DashboardTicketAdapter
    private var ticketList: List<Ticket> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        dashboardViewModel.loadDashboardTickets()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return container?.inflateView(R.layout.fragment_dashboard)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_filter_option.formatString(R.string.ticket_filter_option, getString(R.string.menu_action_all))
        // Empty Ticket msg setup
        textView.text = getString(R.string.ticket_empty_state_msg)
        setupRecycler()
        setupSwipeToRefresh()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_dashboard, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_tickets -> {
                val view = activity?.findViewById<View>(R.id.action_filter_tickets)
                view?.showPopupMenuFilterTickets { itemId ->
                    rv_ticket.scrollToPosition(0)
                    dashboardViewModel.setFilterAction(itemId)
                }
            }
            R.id.action_search -> {
                val searchMenuView: View =
                    (activity as HelpDeskActivity).toolbar.findViewById(R.id.action_search)
                val options = ActivityOptions.makeSceneTransitionAnimation(
                    activity,
                    searchMenuView, getString(R.string.transition_search_back)
                ).toBundle()

                val intent = Intent(activity, SearchTicketActivity::class.java).apply {
                    action = Intent.ACTION_SEARCH
                    putExtras(Bundle().apply {
                        putParcelableArrayListExtra(KEY_TICKETS, ArrayList(ticketList))
                        putInt(KEY_FILTER_ACTION, dashboardViewModel.getActiveFilterAction())
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecycler() {
        dashboardAdapter =
            DashboardTicketAdapter(emptyList()) { ticket -> navigateToTicketDetails(ticket) }
        rv_ticket.apply {
            adapter = dashboardAdapter
        }
    }

    private fun setupObservers() {
        dashboardViewModel.filterAction.observe(viewLifecycleOwner) {
            setTicketFilerOption(it)
            view_empty_state.isVisible = filterItem(it)
        }
        dashboardViewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> tickets_loader.visible(activity?.window)
                NetworkState.LOADED -> tickets_loader.gone(activity?.window)
                else -> {
                    tickets_loader.gone(activity?.window)
                    it.msg?.let { it1 -> updateUserView(it1) }
                }
            }
        }
        dashboardViewModel.ticketList.observe(viewLifecycleOwner) {
            ticketList = it
            view_empty_state.isVisible = filterItem(dashboardViewModel.getActiveFilterAction())
        }
    }

    private fun setupSwipeToRefresh() {
        swipe_refresh.apply {
            setOnRefreshListener {
                dashboardViewModel.loadDashboardTickets()
                isRefreshing = false
            }
        }
    }

    private fun updateUserView(message: String) {
        activity?.showToast(message)
    }

    private fun filterItem(menuItemId: Int): Boolean {
        val list = when (menuItemId) {
            R.id.action_open -> ticketList.filter { it.isOpen }
            R.id.action_closed -> ticketList.filter { it.isClosed }
            else -> ticketList
        }
        dashboardAdapter.submitList(list)
        return list.isEmpty()
    }

    private fun setTicketFilerOption(menuItemId: Int) {
        val actionFilterType = when (menuItemId) {
            R.id.action_open -> R.string.menu_action_open
            R.id.action_closed -> R.string.menu_action_closed
            else -> R.string.menu_action_all
        }
        tv_filter_option.formatString(R.string.ticket_filter_option, getString(actionFilterType))
    }

    private fun navigateToTicketDetails(ticket: Ticket) {
        val bundle = Bundle().apply {
            putParcelable(DashboardDetailFragment.KEY_TICKET, ticket)
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.nav_dashboard_details, bundle) }
    }
}
