package com.sts.ssms.ui.search.documents

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sts.ssms.base.ui.adapter.SearchAdapter
import com.sts.ssms.base.ui.fragment.BaseSearchFragment
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import com.sts.ssms.utils.checkStoragePermissionAndDownload
import com.sts.ssms.utils.downloadFile
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDocumentsFragment : BaseSearchFragment<Documents>() {

    override val viewModel: SearchViewModel<Documents> by viewModel<SearchDocumentsViewModel>()

    override fun getAdapter(retryCallback: () -> Unit): SearchAdapter<Documents> =
        SearchDocumentsAdapter(retryCallback) {
            (viewModel as SearchDocumentsViewModel).documentUrl = it
            val context = activity ?: return@SearchDocumentsAdapter
            context.checkStoragePermissionAndDownload(it)
        }

    override val layoutManager: RecyclerView.LayoutManager =
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

    fun onPermissionGranted() {
        (viewModel as SearchDocumentsViewModel).documentUrl?.let { activity?.downloadFile(it) }
    }
}
