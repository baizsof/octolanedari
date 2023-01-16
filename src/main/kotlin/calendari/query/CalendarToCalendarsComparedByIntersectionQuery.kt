package calendari.query

import calendari.calendar.Calendar
import calendari.calendar.EventCandidate
import org.joda.time.Interval
import java.time.LocalDate

class CalendarToCalendarsComparedByIntersectionQuery(
    private val mainCalendar: Calendar,
    private val comparableCalendars: List<Calendar>,
    private val from: LocalDate,
    private val to: LocalDate
) : Query {
    /*
    (A∩B)U(A∩C)
     */

    override fun doQuery(): List<EventCandidate> {
        val eventCandidates: ArrayList<EventCandidate> = arrayListOf()

        val mainCalendarEvents = mainCalendar.getEvents(from, to)
        for (mainCalendarEvent in mainCalendarEvents) {
            for (calendar in comparableCalendars) {
                val calendarEvents = calendar.getEvents(from, to)
                for (calendarEvent in calendarEvents) {
                    val overlappedInterval : Interval? = calendarEvent.interval.overlap(mainCalendarEvent.interval)
                    overlappedInterval?.let { eventCandidates.add(EventCandidate(calendar.calendarName, overlappedInterval)) }
                }
            }
        }
        return eventCandidates
    }
}