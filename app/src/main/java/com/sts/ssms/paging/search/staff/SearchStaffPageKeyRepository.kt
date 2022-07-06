package com.sts.ssms.paging.search.staff

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class SearchStaffPageKeyRepository(private val staffRepository: StaffRepository) :
    PageKeyRepository<StaffUiModel>() {

    override fun getSourceFactory(query: String, filterId: Int): ItemDataSourceFactory<StaffUiModel> {
        return SearchStaffDataSourceFactory(query, staffRepository)
    }

}