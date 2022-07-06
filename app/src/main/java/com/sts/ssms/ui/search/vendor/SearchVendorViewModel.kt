package com.sts.ssms.ui.search.vendor

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.paging.search.vendor.SearchVendorPageKeyRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class SearchVendorViewModel(private val vendorRepository: VendorRepository) :
    SearchViewModel<VendorUiModel>() {

    override val repoResult: LiveData<Listing<VendorUiModel>> = Transformations.map(query) {
        SearchVendorPageKeyRepository(vendorRepository).getItems(it, filterId)
    }
}