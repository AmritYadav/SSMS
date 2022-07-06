package com.sts.ssms.data.helpdesk.myflat.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.society.model.Flat
import com.sts.ssms.ui.helpdesk.myflat.model.MyFlatApiResult

class MyFlatRepository(
    private val localDataSource: MyFlatLocalDataSource,
    private val remoteDataSource: MyFlatRemoteDataSource
) {

    suspend fun flatMembersList(flatId: Int): MyFlatApiResult {
        return when (val result = remoteDataSource.members(flatId)) {
            is Result.Success -> MyFlatApiResult(myFlatResponse = result.data)
            is Result.Error -> MyFlatApiResult(error = result.exception.message)
        }
    }

    fun getFlatsBySociety(): List<Flat>? {
        return localDataSource.getUserFlats()
    }
}