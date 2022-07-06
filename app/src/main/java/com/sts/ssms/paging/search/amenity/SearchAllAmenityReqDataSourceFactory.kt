package com.sts.ssms.paging.search.amenity

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

class SearchAllAmenityReqDataSourceFactory(
    private val query: String,
    private val amenityRepository: AmenityRepository
) : ItemDataSourceFactory<AllAmenityRequest>() {

    override fun getDataSource(): PageKeyedItemDataSource<AllAmenityRequest> =
        PageKeyedAllAmenityReqDataSource(query, amenityRepository)
}