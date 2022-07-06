package com.sts.ssms.ui.search.noticeboard

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.noticeboard.models.Notice
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchNoticeFragment : BaseSearchFragment<Notice>() {

    override val viewModel: SearchViewModel<Notice> by viewModel<SearchNoticeViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<Notice> =
        SearchNoticeAdapter(this, retryCallback)

    override val layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
}