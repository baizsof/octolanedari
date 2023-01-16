package calendari.calendar.filter

import calendari.calendar.Event

interface EventFilter {
    fun doFilter(allEvents: List<Event>): List<Event>
}
