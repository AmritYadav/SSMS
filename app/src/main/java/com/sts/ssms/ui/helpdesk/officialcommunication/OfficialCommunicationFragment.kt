package com.sts.ssms.ui.helpdesk.officialcommunication

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
import com.sts.ssms.ui.helpdesk.officialcommunication.CommDetailFragment.Companion.KEY_COMM_MODEL
import com.sts.ssms.ui.helpdesk.officialcommunication.adapters.CommunicationAdapter
import com.sts.ssms.ui.helpdesk.officialcommunication.models.Communication
import com.sts.ssms.ui.helpdesk.officialcommunication.viewmodels.CommunicationViewModel
import com.sts.ssms.ui.search.SearchCommunicationActivity
import com.sts.ssms.ui.search.officalcommunication.KEY_COMMUNICATIONS
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_official_communication.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OfficialCommunicationFragment : Fragment() {

    private val viewModel by viewModel<CommunicationViewModel>()
    private var commList: List<Communication> = ArrayList()
    private lateinit var communicationAdapter: CommunicationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadCommunications()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_official_communication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservables()
        setupRecycler()
    }

    private fun setupObservables() {
        viewModel.networkState.observe(viewLifecycleOwner) {
            when (it) {
                NetworkState.LOADING -> progress_communication.visible(activity?.window)
                else -> {
                    progress_communication.gone(activity?.window)
                    if (!it.msg.isNullOrEmpty()) updateUserView(it.msg)
                }
            }
        }
        viewModel.communicationsResult.observe(viewLifecycleOwner) {
            if (!it.error.isNullOrEmpty()) {
                updateUserView(it.error)
                toggleEmptyStateVisibility(showEmptyState = true)
                return@observe
            }
            toggleEmptyStateVisibility(showEmptyState = it.communications.isNullOrEmpty())
            if (!it.communications.isNullOrEmpty()) {
                commList = it.communications
                communicationAdapter.submitList(commList)
            }
        }
    }

    private fun setupRecycler() {
        communicationAdapter =
            CommunicationAdapter(mutableListOf()) { comm -> navigateToDetails(comm) }
        rv_communications.apply {
            adapter = communicationAdapter
        }
    }

    private fun toggleEmptyStateVisibility(showEmptyState: Boolean) {
        rv_communications.visibleGone(!showEmptyState)
        view_empty_state.updateEmptyState(showEmptyState, getString(R.string.prompt_empty_communications))
    }

    private fun updateUserView(msg: String) = activity?.showToast(msg)

    private fun navigateToDetails(comm: Communication) {
        val bundle = Bundle().apply {
            putParcelable(KEY_COMM_MODEL, comm)
        }
        view?.let { Navigation.findNavController(it).navigate(R.id.commDetailFragment, bundle) }
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

                val intent = Intent(activity, SearchCommunicationActivity::class.java).apply {
                    action = Intent.ACTION_SEARCH
                    putExtras(Bundle().apply {
                        putParcelableArrayListExtra(KEY_COMMUNICATIONS, ArrayList(commList))
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
