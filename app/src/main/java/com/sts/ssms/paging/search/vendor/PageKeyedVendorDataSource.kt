package com.sts.ssms.paging.search.vendor

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class PageKeyedVendorDataSource(
    private val query: String,
    private val vendorRepository: VendorRepository
) : PageKeyedItemDataSource<VendorUiModel>() {

    override suspend fun fetchItem(page: Int): SearchResult<VendorUiModel> =
        vendorRepository.getItem(page, query, 0)

    override fun getSearchRepository(): SearchRepository<VendorUiModel> = vendorRepository
}