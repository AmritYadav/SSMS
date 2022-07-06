package com.sts.ssms.ui.search.officalcommunication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sts.ssms.R
import com.sts.ssms.ui.helpdesk.officialcommunication.adapters.CommunicationAdapter
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchDetailsActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.fragment_official_communication.*
import java.util.*

const val KEY_COMMUNICATIONS = "COMMUNICATIONS"
const val KEY_COMMUNICATION_SEARCH_ITEM = "COMMUNICATION_ITEM"
private const val REQUEST_TICKET_DETAILS = 0x02001

class SearchCommunicationFragment : Fragment() {

    private lateinit var communicationAdapter: CommunicationAdapter
    private var communications: List<Communication>? = null

    companion object {
        fun newInstance(communications: List<Communication>): SearchCommunicationFragment =
            SearchCommunicationFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(KEY_COMMUNICATIONS, communications as ArrayList)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_official_communication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            communications = it.getParcelableArrayList(KEY_COMMUNICATIONS)
            setupRecycler()
        }
    }

    private fun setupRecycler() {
        communicationAdapter =
            CommunicationAdapter(mutableListOf()) { comm -> navigateToDetails(comm) }
        rv_communications.apply {
            adapter = communicationAdapter
        }
    }

    private fun navigateToDetails(comm: Communication) {
        Intent(activity, SearchDetailsActivity::class.java).apply {
            putExtras(Bundle().apply {
                putParcelable(KEY_COMMUNICATION_SEARCH_ITEM, comm)
                putParcelable(KEY_NAV_TYPE, NavType.OFFICIAL_COMMUNICATION)
            })
            startActivity(this)
        }
    }

    fun search(query: String) {
        communications?.let { communicationList ->
            val list = communicationList.filter { it.subject.contains(query, ignoreCase = true) }
            communicationAdapter.submitList(list)
            view_empty_state.updateEmptyState(list.isEmpty(), getString(R.string.prompt_empty_communications))
        }
    }
}
