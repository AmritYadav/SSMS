package com.sts.ssms.data.helpdesk.amenity.repository

import com.sts.ssms.data.database.dao.EventDao
import com.sts.ssms.data.database.entity.EventEntity

class LocalAmenityDataSource(private val eventDao: EventDao) {

    fun saveEvents(events: List<EventEntity>) {
        eventDao.saveEvents(events)
    }

    fun getEvents() = eventDao.allEvents()

    fun deleteAllEvents() = eventDao.clearEvents()
}