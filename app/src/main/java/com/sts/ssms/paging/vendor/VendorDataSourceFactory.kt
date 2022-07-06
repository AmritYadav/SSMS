package com.sts.ssms.paging.vendor

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.vendor.repository.VendorRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class VendorDataSourceFactory(private val vendorRepository: VendorRepository) :
    DataSource.Factory<Int, VendorUiModel>() {

    val vendorDataSource = MutableLiveData<VendorDataSource>()

    override fun create(): DataSource<Int, VendorUiModel> {
        val dataSource = VendorDataSource(vendorRepository)
        vendorDataSource.postValue(dataSource)
        return dataSource
    }
}