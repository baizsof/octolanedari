package octolendari.calendar.mapper.google

import octolendari.calendar.mapper.EventMapper
import com.google.api.services.calendar.model.Event
import octolendari.calendar.event.ReservedEvent
import org.joda.time.Interval

class GooglePrivateApiEventMapper : EventMapper<Event> {
    override fun getEvent(event: Event): ReservedEvent {
        val interval: Interval? = try {
            Interval(event.start.dateTime.value, event.end.dateTime.value)
        } catch (ex: NullPointerException) {
            Interval(event.start.date.value, event.end.date.value)
        }
        return ReservedEvent(
            interval = interval!!,
            owner = event.organizer.email,
            name = "asdas",
            calendar = "google"
        )
    }
}