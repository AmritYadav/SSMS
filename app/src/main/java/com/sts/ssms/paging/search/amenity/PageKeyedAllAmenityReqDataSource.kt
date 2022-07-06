package com.sts.ssms.paging.search.amenity

import com.sts.ssms.base.repository.paging.PageKeyedItemDataSource
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

class PageKeyedAllAmenityReqDataSource(
    private val query: String,
    private val amenityRepository: AmenityRepository
) : PageKeyedItemDataSource<AllAmenityRequest>() {

    override suspend fun fetchItem(page: Int): SearchResult<AllAmenityRequest> =
        amenityRepository.getItem(page, query, 0)

    override fun getSearchRepository(): SearchRepository<AllAmenityRequest> = amenityRepository
}