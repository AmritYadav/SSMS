package com.sts.ssms.paging.search.staff

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class SearchStaffDataSourceFactory(
    private val query: String,
    private val staffRepository: StaffRepository
) : ItemDataSourceFactory<StaffUiModel>() {

    override fun getDataSource(): PageKeyedItemDataSource<StaffUiModel> {
        return PageKeyedStaffDataSource(query, staffRepository)
    }
}