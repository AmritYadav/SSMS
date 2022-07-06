package com.sts.ssms.ui.search.amenity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchAllAmenityReqFragment : BaseSearchFragment<AllAmenityRequest>() {

    override val viewModel: SearchViewModel<AllAmenityRequest> by viewModel<SearchAllAmenityReqViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<AllAmenityRequest> =
        SearchAllAmenityReqAdapter(retryCallback)

    override val layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

}