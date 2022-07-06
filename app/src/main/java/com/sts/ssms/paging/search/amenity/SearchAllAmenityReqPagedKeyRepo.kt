package com.sts.ssms.paging.search.amenity

import com.sts.ssms.base.repository.paging.ItemDataSourceFactory
import com.sts.ssms.base.repository.paging.PageKeyRepository
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

class SearchAllAmenityReqPagedKeyRepo(private val amenityRepository: AmenityRepository) :
    PageKeyRepository<AllAmenityRequest>() {

    override fun getSourceFactory(
        query: String, filterId: Int
    ): ItemDataSourceFactory<AllAmenityRequest> =
        SearchAllAmenityReqDataSourceFactory(query, amenityRepository)
}