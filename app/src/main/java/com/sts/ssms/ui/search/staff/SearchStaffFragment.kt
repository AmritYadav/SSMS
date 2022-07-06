package com.sts.ssms.ui.search.staff

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchStaffFragment : BaseSearchFragment<StaffUiModel>() {

    override val viewModel: SearchViewModel<StaffUiModel> by viewModel<SearchStaffViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<StaffUiModel> =
        SearchStaffAdapter(this, retryCallback)

    override val layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

}
