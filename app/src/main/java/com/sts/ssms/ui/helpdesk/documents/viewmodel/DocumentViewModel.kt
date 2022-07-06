package com.sts.ssms.ui.helpdesk.documents.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sts.ssms.NetworkState
import com.sts.ssms.paging.documents.DocumentsDataSourceFactory
import com.sts.ssms.ui.helpdesk.documents.model.Documents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

private const val PAGE_SIZE = 10

class DocumentViewModel(
    private val documentsDataSourceFactory: DocumentsDataSourceFactory
) : ViewModel() {

    private var pagedListBuilder: LivePagedListBuilder<Int, Documents>

    lateinit var documentList: LiveData<PagedList<Documents>>

    var networkState: LiveData<NetworkState>? = null
    var refreshState: LiveData<NetworkState>? = null

    var documentUrl: String? = null

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
        pagedListBuilder = LivePagedListBuilder(documentsDataSourceFactory, config)
        networkState =
            Transformations.switchMap(documentsDataSourceFactory.documentDataSource) { it.getNetworkState() }
        refreshState =
            Transformations.switchMap(documentsDataSourceFactory.documentDataSource) { it.refreshState() }
    }

    fun initDocumentsPager(flatId: Int) {
        documentsDataSourceFactory.getRepository().setFlatId(flatId)
        documentList = pagedListBuilder.build()
    }

    fun retry() = documentsDataSourceFactory.documentDataSource.value?.retry()

    fun refreshAllData() = documentsDataSourceFactory.documentDataSource.value?.refresh()

    fun filterDocuments(flatId: Int) =
        documentsDataSourceFactory.documentDataSource.value?.setAndLoadDocumentByFlat(flatId)

    override fun onCleared() {
        super.onCleared()
        documentsDataSourceFactory.documentDataSource.value?.onCleared()
    }
}
