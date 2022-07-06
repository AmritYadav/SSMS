package com.sts.ssms.data.helpdesk.amenity.repository

import com.sts.ssms.data.common.Result
import com.sts.ssms.data.database.entity.EventEntity
import com.sts.ssms.data.helpdesk.amenity.model.*
import com.sts.ssms.data.search.api.SearchResult
import com.sts.ssms.data.search.repository.SearchRepository
import com.sts.ssms.ui.helpdesk.amenity.model.*
import kotlinx.coroutines.runBlocking

class AmenityRepository(
    private val localAmenityDataSource: LocalAmenityDataSource,
    private val remoteAmenityDataSource: RemoteAmenityDataSource
) : SearchRepository<AllAmenityRequest> {

    private var societyBuildingId: String = ""

    fun setSocietyBuildingId(id: String) {
        societyBuildingId = id
    }

    fun getSocietyBuildingId():String{
        return societyBuildingId
    }

    suspend fun getAllRequestedAmenities(page: Int): AllAmenityRequestResult {
        val result = remoteAmenityDataSource.allAmenityRequests(page)
        return if (result is Result.Success)
            AllAmenityRequestResult(allRequests = result.data.map { it.toAllAmenityRequest() })
        else AllAmenityRequestResult(error = (result as Result.Error).exception.message)
    }

    suspend fun getSocietyAmenities(page: Int): SocietyAmenitiesResult {
        val result = remoteAmenityDataSource.societyAmenities(page, societyBuildingId)
        return if (result is Result.Success)
            SocietyAmenitiesResult(allAmenities = result.data.map { it.toSocietyAmenity() })
        else SocietyAmenitiesResult(error = (result as Result.Error).exception.message)
    }

    suspend fun getSocietyBuildingList(): List<SocietyBuilding> {
        val result = remoteAmenityDataSource.societyBuildings()
        if (result is Result.Success) return loadSocietyBuildingWithDefaultType(result.data)
        return loadSocietyBuildingWithDefaultType(emptyList())
    }

    suspend fun loadEvents(): EventResult =
        when (val response = remoteAmenityDataSource.myAmenities()) {
            is Result.Success -> {
                saveEvents(response.data.filter { it.from.isNotEmpty() }.map { it.toEventEntity() })
                EventResult(loadEvent = true)
            }
            is Result.Error -> EventResult(error = response.exception.message)
        }

    suspend fun myAmenities(): MyAmenityResult =
        when (val result = remoteAmenityDataSource.myAmenities()) {
            is Result.Success -> MyAmenityResult(myAmenities = result.data.map { it.toMyAmenity() })
            is Result.Error -> MyAmenityResult(error = result.exception.message)
        }

    fun allEvents() = localAmenityDataSource.getEvents()

    private fun saveEvents(events: List<EventEntity>) = runBlocking {
        localAmenityDataSource.saveEvents(events)
        return@runBlocking
    }

    fun clearEvents() = runBlocking {
        localAmenityDataSource.deleteAllEvents()
    }

    private fun loadSocietyBuildingWithDefaultType(societyBuildings: List<SocietyBuilding>): List<SocietyBuilding> {
        val types = mutableListOf(SocietyBuilding("", "All Amenities"))
        if (societyBuildings.isNotEmpty()) types.addAll(societyBuildings)
        return types
    }

    override suspend fun getItem(
        page: Int, query: String, filterId: Int
    ): SearchResult<AllAmenityRequest> {
        return when (val result = remoteAmenityDataSource.allAmenityRequests(page, query)) {
            is Result.Success -> SearchResult(itemList = result.data.map { it.toAllAmenityRequest() })
            is Result.Error -> SearchResult(error = result.exception.message)
        }
    }

    suspend fun amenityCategories() =
        when (val result = remoteAmenityDataSource.amenityCategory()) {
            is Result.Success -> AmenityCategoryResult(categories = result.data.map { it.toAmenityCategory() })
            is Result.Error -> AmenityCategoryResult(error = result.exception.message)
        }

    suspend fun saveAmenityRequest(requestBody: AmenityRequestBody): AmenityRequestResult =
        when (val result = remoteAmenityDataSource.saveAmenityRequest(requestBody)) {
            is Result.Success -> AmenityRequestResult(success = result.data.success)
            is Result.Error -> AmenityRequestResult(error = result.exception.message)
        }
}