package com.sts.ssms.ui.search.ticket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.dashboard.adapter.DashboardTicketAdapter
import com.sts.ssms.ui.helpdesk.dashboard.model.Ticket
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.KEY_TICKET_SEARCH_ITEM
import com.sts.ssms.ui.search.SearchDetailsActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.visibleGone
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.layout_empty_state.*
import java.util.*

const val KEY_TICKETS = "TICKETS"
const val KEY_FILTER_ACTION = "FILTER_ACTION"
private const val REQUEST_TICKET_DETAILS = 0x02001

class SearchTicketFragment : Fragment() {

    private lateinit var dashboardAdapter: DashboardTicketAdapter
    private var tickets: List<Ticket>? = null
    private var filterAction: Int? = null

    companion object {
        fun newInstance(tickets: List<Ticket>, filterAction: Int): SearchTicketFragment =
            SearchTicketFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_TICKETS, tickets as ArrayList)
                    putInt(KEY_FILTER_ACTION, filterAction)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            tickets = it.getParcelableArrayList(KEY_TICKETS)
            filterAction = it.getInt(KEY_FILTER_ACTION)
            swipe_refresh.isEnabled = false
            tv_filter_option.visibleGone(false)

            setupRecycler(tickets!!)
        }
    }

    private fun setupRecycler(list: List<Ticket>) {
        dashboardAdapter =
            DashboardTicketAdapter(list) { ticket -> navigateToTicketDetails(ticket) }
        rv_ticket.apply {
            adapter = dashboardAdapter
        }
    }

    private fun navigateToTicketDetails(ticket: Ticket) {
        val intent = Intent(activity, SearchDetailsActivity::class.java).apply {
            putExtras(Bundle().apply {
                putParcelable(KEY_TICKET_SEARCH_ITEM, ticket)
                putParcelable(KEY_NAV_TYPE, NavType.TICKET)
            })
        }
        startActivityForResult(intent, REQUEST_TICKET_DETAILS)
    }

    fun search(query: String) {
        tickets?.let { ticketsList ->
            val list = ticketsList.filter { it.issue.contains(query, ignoreCase = true) }
            textView.text = getString(R.string.ticket_empty_state_msg)
            view_empty_state.isVisible = filterItem(list)
        }
    }

    private fun filterItem(ticketList: List<Ticket>): Boolean {
        val list = when (filterAction) {
            R.id.action_open -> ticketList.filter { it.isOpen }
            R.id.action_closed -> ticketList.filter { it.isClosed }
            else -> ticketList
        }
        dashboardAdapter.submitList(list)
        return list.isEmpty()
    }
}
