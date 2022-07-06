package com.sts.ssms.data.helpdesk.staff.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.helpdesk.staff.api.StaffRatingRequest
import com.sts.ssms.data.helpdesk.staff.api.toStaffUiModel
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.staff.model.StaffDetailsResult
import com.sts.ssms.ui.helpdesk.staff.model.StaffResult
import com.sts.ssms.ui.helpdesk.staff.model.StaffUiModel
import com.sts.ssms.ui.helpdesk.vendor.model.VendorStaffRatingResult

class StaffRepository(private val remoteDataSource: StaffRemoteDataSource) :
    SearchRepository<StaffUiModel> {

    suspend fun staffList(page: Int): StaffResult {
        return when (val result = remoteDataSource.staffList(page)) {
            is Result.Success -> StaffResult(staffList = result.data.results.map { it.toStaffUiModel() })
            is Result.Error -> StaffResult(error = result.exception.message)
        }
    }

    suspend fun staffRatings(request: StaffRatingRequest): VendorStaffRatingResult {
        return when (val result = remoteDataSource.staffRatingComment(request)) {
            is Result.Success -> VendorStaffRatingResult(success = result.data.success)
            is Result.Error -> VendorStaffRatingResult(error = result.exception.message)
        }
    }

    suspend fun staff(staffId: Int): StaffDetailsResult {
        return when (val result = remoteDataSource.staffDetails(staffId)) {
            is Result.Success -> StaffDetailsResult(staffDetails = result.data.toStaffUiModel())
            is Result.Error -> StaffDetailsResult(error = result.exception.message)
        }
    }

    override suspend fun getItem(
        page: Int,
        query: String,
        filterId: Int
    ): SearchResult<StaffUiModel> {
        return when (val result = remoteDataSource.staffList(page, query)) {
            is Result.Success -> SearchResult(itemList = result.data.results.map { it.toStaffUiModel() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }
}