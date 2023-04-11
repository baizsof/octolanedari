package octolendari.calendar.connector

import octolendari.calendar.event.Event
import java.time.LocalDate

interface CalendarConnector {
    fun getEvents(from: LocalDate, to: LocalDate): List<Event>
}