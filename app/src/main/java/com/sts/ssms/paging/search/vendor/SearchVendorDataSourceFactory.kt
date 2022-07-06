package com.sts.ssms.paging.search.vendor

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class SearchVendorDataSourceFactory(
    private val query: String,
    private val vendorRepository: VendorRepository
) : ItemDataSourceFactory<VendorUiModel>() {

    override fun getDataSource(): PageKeyedItemDataSource<VendorUiModel> =
        PageKeyedVendorDataSource(query, vendorRepository)
}