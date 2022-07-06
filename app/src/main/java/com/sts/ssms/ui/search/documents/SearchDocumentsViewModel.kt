package com.sts.ssms.ui.search.documents

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.documents.repository.DocumentsRepository
import com.sts.ssms.paging.search.documents.SearchDocumentsPagedKeyRepository
import com.sts.ssms.ui.helpdesk.documents.model.Documents

class SearchDocumentsViewModel(private val documentsRepository: DocumentsRepository) :
    SearchViewModel<Documents>() {

    var documentUrl: String? = null

    override val repoResult: LiveData<Listing<Documents>> = Transformations.map(query) {
        SearchDocumentsPagedKeyRepository(documentsRepository).getItems(it, filterId)
    }
}
