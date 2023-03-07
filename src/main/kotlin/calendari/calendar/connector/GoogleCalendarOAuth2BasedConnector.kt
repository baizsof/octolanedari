package calendari.calendar.connector

import calendari.calendar.Event
import java.time.LocalDate

class GoogleCalendarOAuth2BasedConnector : CalendarConnector{
    override fun getEvents(from: LocalDate, to: LocalDate): List<Event> {
        TODO("Not yet implemented")
    }
}