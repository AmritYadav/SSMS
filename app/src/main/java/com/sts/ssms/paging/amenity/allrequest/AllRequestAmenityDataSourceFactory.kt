package com.sts.ssms.paging.amenity.allrequest

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

class AllRequestAmenityDataSourceFactory(private val amenityRepository: AmenityRepository) :
    DataSource.Factory<Int, AllAmenityRequest>() {

    val dataSourceLiveData = MutableLiveData<AllRequestAmenityDataSource>()

    override fun create(): DataSource<Int, AllAmenityRequest> {
        val dataSource = AllRequestAmenityDataSource(amenityRepository)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }
}