package com.sts.ssms.ui.search.photogallery

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.photogallery.model.AlbumUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchAlbumFragment : BaseSearchFragment<AlbumUiModel>() {
    override val viewModel: SearchViewModel<AlbumUiModel> by viewModel<SearchAlbumViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<AlbumUiModel> =
        SearchAlbumAdapter(this, retryCallback)

    override val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(activity, 2)

}
