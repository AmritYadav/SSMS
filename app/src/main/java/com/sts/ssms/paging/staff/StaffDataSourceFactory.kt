package com.sts.ssms.paging.staff

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.staff.repository.StaffRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel

class StaffDataSourceFactory (private val staffRepository: StaffRepository) :
    DataSource.Factory<Int, StaffUiModel>() {

    val staffDataSourceLiveData = MutableLiveData<StaffDataSource>()

    override fun create(): DataSource<Int, StaffUiModel> {
        val staffDataSource = StaffDataSource(staffRepository)
        staffDataSourceLiveData.postValue(staffDataSource)
        return staffDataSource
    }
}