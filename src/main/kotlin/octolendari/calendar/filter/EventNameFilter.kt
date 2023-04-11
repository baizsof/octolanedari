package octolendari.calendar.filter

import octolendari.calendar.event.Event

class EventNameFilter(private val criteria: String) : EventFilter {

    override fun filter(events: List<Event>): List<Event> {
        TODO("Not yet implemented")
    }
}