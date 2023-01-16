package calendari.calendar.filter

import calendari.calendar.Event

class AllEventFilter : EventFilter {
    override fun doFilter(allEvents: List<Event>): List<Event> {
        return allEvents
    }

}
