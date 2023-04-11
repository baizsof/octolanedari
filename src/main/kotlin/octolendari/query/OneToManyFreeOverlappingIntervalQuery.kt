package octolendari.query

import octolendari.calendar.Calendar
import octolendari.calendar.event.CandidateEvent
import org.joda.time.Interval
import java.time.LocalDate

class OneToManyFreeOverlappingIntervalQuery(
    private val oneCalendar: Calendar,
    private val calendars: List<Calendar>,
    private val from: LocalDate,
    private val to: LocalDate
) : Query {
    /*
    (A∩B)U(A∩C)U..
     */

    override fun doQuery(): List<CandidateEvent> {
        val candidateEvents: ArrayList<CandidateEvent> = arrayListOf()

        val oneCalendarFilteredEvents = oneCalendar.getFilteredEvents(from, to)
        for (oneCalendarFilteredEvent in oneCalendarFilteredEvents) {
            for (calendar in calendars) {
                val calendarFreeIntervals = calendar.getFreeIntervalsBetweenFilteredEvents(from, to)
                for (calendarFreeInterval in calendarFreeIntervals) {
                    val overlappedInterval : Interval? = calendarFreeInterval.interval.overlap(oneCalendarFilteredEvent.interval)
                    overlappedInterval?.let {
                        val candidateEvent = CandidateEvent(calendar.name, overlappedInterval)
                        candidateEvents.add(candidateEvent)
                    }
                }
            }
        }
        return candidateEvents
    }
}