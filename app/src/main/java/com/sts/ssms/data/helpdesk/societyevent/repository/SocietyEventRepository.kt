package com.sts.ssms.data.helpdesk.societyevent.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.ui.helpdesk.societyevent.model.SocietyEventResult
import com.sts.ssms.data.helpdesk.societyevent.api.toSocietyEvent
import com.sts.ssms.ui.helpdesk.societyevent.model.EventPostResult

class SocietyEventRepository(
    private val remoteDataSource: RemoteSocietyEventDataSource
) {

    suspend fun loadSocietyEvent(page: Int, perPage: Int = 10): SocietyEventResult {
        return when (val result = remoteDataSource.allSocietyEvent(page, perPage)) {
            is Result.Success -> SocietyEventResult(
                societyEventList = result.data.societyEventList.map { it.toSocietyEvent() })
            is Result.Error -> SocietyEventResult(
                error = result.exception.message
            )
        }
    }

    suspend fun postSuggestEvent(title: String, description: String): EventPostResult {
        val result = remoteDataSource.postSuggestEvent(title, description)
        if (result is Result.Success)
            return EventPostResult(
                success = result.data.success
            )
        result as Result.Error
        return EventPostResult(error = result.exception.message)
    }

}