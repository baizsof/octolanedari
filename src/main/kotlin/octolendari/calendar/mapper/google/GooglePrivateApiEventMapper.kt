package octolendari.calendar.mapper.google

import octolendari.calendar.mapper.EventMapper
import com.google.api.services.calendar.model.Event
import org.joda.time.Interval

class GooglePrivateApiEventMapper : EventMapper<Event> {
    override fun getEvent(event: Event): octolendari.calendar.Event {
        val interval : Interval? = try {
            Interval(event.start.dateTime.value, event.end.dateTime.value)
        } catch (ex: NullPointerException) {
            Interval(event.start.date.value, event.end.date.value)
        }
        return octolendari.calendar.Event(interval!!, event.organizer.email)
    }
}