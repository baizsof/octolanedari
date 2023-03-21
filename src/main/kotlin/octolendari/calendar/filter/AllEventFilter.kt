package octolendari.calendar.filter

import octolendari.calendar.Event

class AllEventFilter : EventFilter {
    override fun doFilter(allEvents: List<Event>): List<Event> {
        return allEvents
    }

}
