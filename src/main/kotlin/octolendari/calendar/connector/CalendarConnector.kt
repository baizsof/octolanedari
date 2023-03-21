package octolendari.calendar.connector

import octolendari.calendar.Event
import java.time.LocalDate

interface CalendarConnector {
    fun getEvents(from: LocalDate, to: LocalDate) : List<Event>
}