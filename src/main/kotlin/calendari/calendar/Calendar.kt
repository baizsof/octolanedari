package calendari.calendar

import calendari.calendar.connector.CalendarConnector
import calendari.calendar.filter.AllEventFilter
import calendari.calendar.filter.EventFilter
import org.joda.time.DateTime
import org.joda.time.Interval
import calendari.calendar.interval.searcher.GapIntervalSearcher
import java.time.LocalDate

open class Calendar(
    private val calendarConnector: CalendarConnector,
    val calendarName: String,
    private val eventFilter : EventFilter = AllEventFilter()
) {
    fun getEvents(from: LocalDate, to: LocalDate): List<Event> {
        val allEvents = calendarConnector.getEvents(from, to)
        return eventFilter.doFilter(allEvents)
    }

    fun getEventsIntervals(from: LocalDate, to: LocalDate): List<Interval> {
        val events = getEvents(from, to)
        val intervals: ArrayList<Interval> = arrayListOf()
        for (event in events) {
            intervals.add(event.interval)
        }
        return intervals
    }

    fun getEventCandidates(start: LocalDate, end: LocalDate): List<EventCandidate> {
        val sortedIntervals = getEventsIntervals(start, end).sortedBy { it.start }
        val searchInterval = Interval(
            createDateTime(start),
            createDateTime(end)
        )

        val freeIntervals = GapIntervalSearcher().search(
            searchInterval,
            sortedIntervals
        )

        return freeIntervals.map { EventCandidate(calendarName, it) }
    }

    private fun createDateTime(from: LocalDate) = DateTime(from.year, from.monthValue, from.dayOfMonth, 0, 0)
    fun getOverlappingEventCandidates(
        otherCalendar: Calendar,
        start: LocalDate,
        end: LocalDate
    ): ArrayList<EventCandidate> {
        val thisEventCandidates = getEventCandidates(start, end)
        val otherEventCandidates = otherCalendar.getEventCandidates(start, end)

        val overlappingEventCandidates = arrayListOf<EventCandidate>()
        for (thisEventCandidate in thisEventCandidates) {
            for (otherEventCandidate in otherEventCandidates) {
                val overlappedInterval = thisEventCandidate.interval.overlap(otherEventCandidate.interval)
                if (overlappedInterval != null) {
                    overlappingEventCandidates.add(EventCandidate(otherCalendar.calendarName, overlappedInterval))
                }
            }
        }
        return overlappingEventCandidates
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Calendar

        if (calendarConnector != other.calendarConnector) return false
        if (calendarName != other.calendarName) return false
        if (eventFilter != other.eventFilter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = calendarConnector.hashCode()
        result = 31 * result + calendarName.hashCode()
        result = 31 * result + eventFilter.hashCode()
        return result
    }


}