package com.sts.ssms.ui.helpdesk.documents

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.sts.ssms.NetworkState
import com.sts.ssms.R
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.ui.helpdesk.HelpDeskActivity
import com.sts.ssms.ui.helpdesk.HelpDeskViewModel
import com.sts.ssms.ui.helpdesk.documents.adapter.DocumentsAdapter
import com.sts.ssms.ui.helpdesk.documents.viewmodel.DocumentViewModel
import com.sts.ssms.ui.search.KEY_FILTER_ID
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.utils.*
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_documents.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DocumentsFragment : Fragment() {

    private val viewModel by viewModel<DocumentViewModel>()
    private val helpDeskViewModel by activityViewModels<HelpDeskViewModel>()

    private lateinit var documentsAdapter: DocumentsAdapter
    private lateinit var flatList: List<Flat>
    private lateinit var flatDisplayName: String
    private var flatId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val initialId = setupFlatList()
        flatId = initialId
        viewModel.initDocumentsPager(initialId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_documents, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flat_id.text = flatDisplayName
        setHasOptionsMenu(true)
        setupObservables()
        configureRecycler()
        configureSwipeRefresh()
        flat_id.setOnClickListener {
            it.showDocumentsPopupWindow(flatList) { flat ->
                flatId = flat.flatId
                flatDisplayName = flat.display
                viewModel.filterDocuments(flat.flatId)
            }
        }
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
                        putParcelable(KEY_NAV_TYPE, NavType.DOCUMENTS)
                        putInt(KEY_FILTER_ID, flatId)
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupFlatList(): Int {
        flatList = helpDeskViewModel.flatList ?: emptyList()
        if (flatList.isNotEmpty()) flatDisplayName = flatList[0].display
        return if (flatList.isEmpty()) 0 else flatList[0].flatId
    }

    private fun setupObservables() {
        viewModel.networkState?.observe(viewLifecycleOwner) { it ->
            documentsAdapter.updateNetworkState(it)
            viewModel.documentList.value?.let {
                view_empty_state.updateEmptyState(it.isEmpty(), getString(R.string.prompt_empty_document))
            }
        }
        viewModel.documentList.observe(viewLifecycleOwner) {
            documentsAdapter.submitList(it)
        }
    }


    private fun configureRecycler() {
        documentsAdapter = DocumentsAdapter({ viewModel.retry() }, {
            viewModel.documentUrl = it
            val context = activity ?: return@DocumentsAdapter
            context.checkStoragePermissionAndDownload(it)
        })
        rv_documents.apply {
            adapter = documentsAdapter
        }
    }

    private fun configureSwipeRefresh() {
        swipe_refresh.apply {
            viewModel.refreshState?.observe(viewLifecycleOwner) {
                isRefreshing = it == NetworkState.LOADING
            }
            setOnRefreshListener {
                viewModel.refreshAllData()
            }
        }
    }

    fun onPermissionGranted() {
        viewModel.documentUrl?.let { activity?.downloadFile(it) }
    }
}
