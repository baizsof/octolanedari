package octolendari.calendar.filter

import octolendari.calendar.event.Event

interface EventFilter {
    fun filter(events: List<Event>): List<Event>
}
