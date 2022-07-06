package com.sts.ssms.paging.amenity.societyamenity

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.SocietyAmenity

class SocietyAmenityDataSourceFactory(private val amenityRepository: AmenityRepository) :
    DataSource.Factory<Int, SocietyAmenity>() {

    val dataSourceLiveData = MutableLiveData<SocietyAmenityDataSource>()

    override fun create(): DataSource<Int, SocietyAmenity> {
        val dataSource = SocietyAmenityDataSource(amenityRepository)
        dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getRepo() = amenityRepository
}