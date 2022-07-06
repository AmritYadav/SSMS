package com.sts.ssms.data.helpdesk.vendor.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.vendor.api.toVendorUiModel
import com.sts.ssms.data.helpdesk.vendor.api.VendorRatingRequest
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.vendor.model.VendorDetailsResult
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult
import com.sts.ssms.ui.helpdesk.vendor.model.VendorResult
import com.sts.ssms.ui.helpdesk.vendor.model.VendorUiModel

class VendorRepository(private val remoteDataSource: VendorRemoteDataSource) :
    SearchRepository<VendorUiModel> {

    suspend fun vendorsList(page: Int): VendorResult {
        return when (val result = remoteDataSource.vendors(page)) {
            is Result.Success -> VendorResult(vendorList = result.data.results.map { it.toVendorUiModel() })
            is Result.Error -> VendorResult(error = result.exception.message)
        }
    }

    suspend fun vendorRatings(request: VendorRatingRequest): VendorStaffRatingResult {
        return when (val result = remoteDataSource.vendorRatingComment(request)) {
            is Result.Success -> VendorStaffRatingResult(success = result.data.success)
            is Result.Error -> VendorStaffRatingResult(error = result.exception.message)
        }
    }

    suspend fun vendor(vendorId: Int): VendorDetailsResult {
        return when (val result = remoteDataSource.vendorDetails(vendorId)) {
            is Result.Success -> VendorDetailsResult(vendorDetails = result.data.toVendorUiModel())
            is Result.Error -> VendorDetailsResult(error = result.exception.message)
        }
    }

    override suspend fun getItem(
        page: Int,
        query: String,
        filterId: Int
    ): SearchResult<VendorUiModel> {
        return when (val result = remoteDataSource.vendors(page, query)) {
            is Result.Success -> SearchResult(itemList = result.data.results.map { it.toVendorUiModel() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }
}