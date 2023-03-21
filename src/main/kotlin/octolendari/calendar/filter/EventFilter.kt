package octolendari.calendar.filter

import octolendari.calendar.Event

interface EventFilter {
    fun doFilter(allEvents: List<Event>): List<Event>
}
