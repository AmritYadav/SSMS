package com.sts.ssms.paging.search.vendor

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class SearchVendorPageKeyRepository(private val vendorRepository: VendorRepository) :
    PageKeyRepository<VendorUiModel>() {

    override fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<VendorUiModel> =
        SearchVendorDataSourceFactory(query, vendorRepository)
}
