package octolendari.calendar.filter

import octolendari.calendar.Event

class EventNameFilter(private val criteria: String) : EventFilter {

    override fun doFilter(allEvents: List<Event>): List<Event> {
        return allEvents.filter { event -> event.name == criteria }
    }
}