package calendari.calendar.connector

import calendari.calendar.Event
import java.time.LocalDate

interface CalendarConnector {
    fun getEvents(from: LocalDate, to: LocalDate) : List<Event>
}