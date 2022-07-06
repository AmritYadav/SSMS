package com.sts.ssms.ui.search.staff

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.paging.search.staff.SearchStaffPageKeyRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class SearchStaffViewModel(private val staffRepository: StaffRepository) :
    SearchViewModel<StaffUiModel>() {

    override val repoResult: LiveData<Listing<StaffUiModel>> = Transformations.map(query) {
        SearchStaffPageKeyRepository(staffRepository).getItems(it, filterId)
    }
}