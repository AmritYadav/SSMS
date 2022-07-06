package com.sts.ssms.paging.societyevent

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.societyevent.repository.SocietyEventRepository
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEvent

class SocietyEventDataSourceFactory(private val societyEventRepository: SocietyEventRepository) :
    DataSource.Factory<Int, SocietyEvent>() {

    val societyEventDataSource = MutableLiveData<SocietyEventDataSource>()

    override fun create(): DataSource<Int, SocietyEvent> {
        val dataSource = SocietyEventDataSource(societyEventRepository)
        societyEventDataSource.postValue(dataSource)
        return dataSource
    }

    fun getRepository() = societyEventRepository

}