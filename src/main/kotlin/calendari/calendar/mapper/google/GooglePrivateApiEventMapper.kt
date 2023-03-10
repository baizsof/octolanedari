package calendari.calendar.mapper.google

import calendari.calendar.mapper.EventMapper
import com.google.api.services.calendar.model.Event

class GooglePrivateApiEventMapper : EventMapper<Event> {
    override fun getEvent(event : Event): calendari.calendar.Event {
        TODO("Not yet implemented")
    }
}