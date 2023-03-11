package calendari.calendar.mapper.google

import calendari.calendar.mapper.EventMapper
import com.google.api.services.calendar.model.Event
import org.joda.time.Interval

class GooglePrivateApiEventMapper : EventMapper<Event> {
    override fun getEvent(event: Event): calendari.calendar.Event {
        val interval : Interval? = try {
            Interval(event.start.dateTime.value, event.end.dateTime.value)
        } catch (ex: NullPointerException) {
            Interval(event.start.date.value, event.end.date.value)
        }
        return calendari.calendar.Event(interval!!, event.organizer.email)
    }
}