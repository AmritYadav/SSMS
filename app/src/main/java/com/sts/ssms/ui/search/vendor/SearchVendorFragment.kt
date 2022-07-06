package com.sts.ssms.ui.search.vendor

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchVendorFragment : BaseSearchFragment<VendorUiModel>() {

    override val viewModel: SearchViewModel<VendorUiModel> by viewModel<SearchVendorViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<VendorUiModel> =
        SearchVendorAdapter(this, retryCallback)

    override val layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

}
