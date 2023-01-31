package calendari.query

import calendari.calendar.Calendar
import calendari.calendar.EventCandidate
import org.joda.time.Interval
import java.time.LocalDate

class OneToManyOverlappingIntervalQuery(
    private val oneCalendar: Calendar,
    private val manyCalendars: List<Calendar>,
    private val from: LocalDate,
    private val to: LocalDate
) : Query {
    /*
    (A∩B)U(A∩C)U..
     */

    override fun doQuery(): List<EventCandidate> {
        val eventCandidates: ArrayList<EventCandidate> = arrayListOf()

        for (oneCalendarEvent in oneCalendar.getEvents(from, to)) {
            for (manyCalendar in manyCalendars) {
                for (manyCalendarEvent in manyCalendar.getEvents(from, to)) {
                    val overlappedInterval : Interval? = manyCalendarEvent.interval.overlap(oneCalendarEvent.interval)
                    overlappedInterval?.let { eventCandidates.add(EventCandidate(manyCalendar.calendarName, overlappedInterval)) }
                }
            }
        }
        return eventCandidates
    }
}