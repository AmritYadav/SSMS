package com.sts.ssms.ui.helpdesk.noticeboard

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
import com.sts.ssms.ui.helpdesk.noticeboard.adapters.NoticeAdapter
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import com.sts.ssms.ui.helpdesk.noticeboard.models.NoticeType
import com.sts.ssms.ui.helpdesk.noticeboard.viewmodels.NoticeViewModel
import com.sts.ssms.ui.search.KEY_FILTER_ID
import com.sts.ssms.ui.search.KEY_NAV_TYPE
import com.sts.ssms.ui.search.SearchActivity
import com.sts.ssms.utils.NavType
import com.sts.ssms.utils.showNoticePopupWindow
import com.sts.ssms.utils.updateEmptyState
import kotlinx.android.synthetic.main.app_bar_help_desk.*
import kotlinx.android.synthetic.main.fragment_notice_board.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoticeBoardFragment : Fragment() {

    private val viewModel by viewModel<NoticeViewModel>()
    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var noticeTypes: List<NoticeType>
    private var noticeId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_notice_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadNoticeTypes()
        noticeId = viewModel.getNoticeType()
        configureRecycler()
        setupObservables()
        configureSwipeRefresh()

        notice_type.setOnClickListener {
            it.showNoticePopupWindow(noticeTypes) { id ->
                noticeId = id
                viewModel.loadNotice(id)
            }
        }
    }

    private fun setupObservables() {
        viewModel.networkState?.observe(viewLifecycleOwner) { it ->
            noticeAdapter.updateNetworkState(it)
            viewModel.noticeList.value?.let {
                view_empty_state.updateEmptyState(
                    it.isEmpty(),
                    getString(R.string.prompt_empty_notice)
                )
            }
        }
        viewModel.noticeList.observe(viewLifecycleOwner) {
            noticeAdapter.submitList(it)
        }
        viewModel.noticeTypeResult.observe(viewLifecycleOwner) { noticeTypeResult ->
            noticeTypeResult.noticeTypes?.let {
                noticeTypes = it
                notice_type.text = it.filter { noticeType -> noticeType.id == noticeId }[0].name
            }
        }
    }

    private fun configureRecycler() {
        noticeAdapter =
            NoticeAdapter({ viewModel.retry() }, { notice -> navigateToNoticeDetails(notice) })
        rv_notice.apply {
            adapter = noticeAdapter
        }
    }

    private fun configureSwipeRefresh() {
        var isSwipeEnabled = false
        swipe_refresh.apply {

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

    private fun navigateToNoticeDetails(notice: Notice) {
        val bundle = Bundle().apply {
            putParcelable(NoticeDetailsFragment.KEY_NOTICE_MODEL, notice)
        }
        view?.let {
            Navigation.findNavController(it)
                .navigate(R.id.noticeDetailsFragment, bundle)
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
                        putParcelable(KEY_NAV_TYPE, NavType.NOTICE_BOARD)
                        putInt(KEY_FILTER_ID, noticeId)
                    })
                }
                startActivity(intent, options)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
