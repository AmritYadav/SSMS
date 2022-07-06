package com.sts.ssms.data.event

import com.sts.ssms.custom.calendar.helpers.EventType
import com.sts.ssms.custom.calendar.models.Event
import com.sts.ssms.data.database.entity.toEvent
import com.sts.ssms.data.helpdesk.amenity.repository.AmenityRepository
import com.sts.ssms.data.helpdesk.societyevent.repository.SocietyEventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventRepository(
    private val amenityRepository: AmenityRepository
) {

    private var eventType = EventType.AMENITY

    fun setEventType(type: EventType?) {
        eventType = type ?: eventType
    }

    fun loadEvents(callback: (events: List<Event>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val events = when (eventType) {
                EventType.AMENITY -> amenityRepository.allEvents()
            }
            callback.invoke(events.map { it.toEvent() })
        }
    }

}