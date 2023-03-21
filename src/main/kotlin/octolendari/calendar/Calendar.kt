package octolendari.calendar

import octolendari.calendar.connector.CalendarConnector
import octolendari.calendar.filter.AllEventFilter
import octolendari.calendar.filter.EventFilter
import octolendari.calendar.interval.searcher.FreeIntervalsSearcher
import org.joda.time.DateTime
import org.joda.time.Interval
import java.time.LocalDate

open class Calendar(
    private val calendarConnector: CalendarConnector,
    val name: String,
    private val eventFilter: EventFilter = AllEventFilter()
) {
    fun getFilteredEvents(from: LocalDate, to: LocalDate): List<Event> {
        val allEvents = calendarConnector.getEvents(from, to)
        return eventFilter.doFilter(allEvents)
    }

    fun getFreeIntervalsBetweenFilteredEvents(from: LocalDate, to: LocalDate): List<EventCandidate> {
        val sortedEventsIntervals = getSortedFilteredEventsIntervals(from, to)
        val searchInterval = Interval(
            createDateTime(from),
            createDateTime(to).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59)
        )

        val freeIntervals = FreeIntervalsSearcher().search(
            searchInterval,
            sortedEventsIntervals
        )

        return freeIntervals.map { EventCandidate(name, it) }
    }

    private fun getSortedFilteredEventsIntervals(
        from: LocalDate,
        to: LocalDate
    ) = getFilteredEvents(from, to).map { event: Event -> event.interval }.sortedBy { it.start }

    private fun createDateTime(from: LocalDate) = DateTime(from.year, from.monthValue, from.dayOfMonth, 0, 0)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Calendar

        if (calendarConnector != other.calendarConnector) return false
        if (name != other.name) return false
        if (eventFilter != other.eventFilter) return false

        return true
    }

    override fun hashCode(): Int {
        var result = calendarConnector.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + eventFilter.hashCode()
        return result
    }


}