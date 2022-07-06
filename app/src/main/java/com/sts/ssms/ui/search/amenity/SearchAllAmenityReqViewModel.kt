package com.sts.ssms.ui.search.amenity

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.sts.ssms.base.repository.Listing
import com.sts.ssms.base.ui.viewmodel.SearchViewModel
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.paging.search.amenity.SearchAllAmenityReqPagedKeyRepo
import com.sts.ssms.ui.helpdesk.amenity.model.AllAmenityRequest

class SearchAllAmenityReqViewModel(val amenityRepository: AmenityRepository) :
    SearchViewModel<AllAmenityRequest>() {

    override val repoResult: LiveData<Listing<AllAmenityRequest>> = Transformations.map(query) {
        SearchAllAmenityReqPagedKeyRepo(amenityRepository).getItems(it, filterId)
    }

}