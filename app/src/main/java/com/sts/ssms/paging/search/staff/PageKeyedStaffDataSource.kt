package com.sts.ssms.paging.search.staff

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class PageKeyedStaffDataSource(
    private val query: String,
    private val staffRepository: StaffRepository
) : PageKeyedItemDataSource<StaffUiModel>() {

    override suspend fun fetchItem(page: Int): SearchResult<StaffUiModel> =
        staffRepository.getItem(page, query, 0)

    override fun getSearchRepository(): SearchRepository<StaffUiModel> = staffRepository

}