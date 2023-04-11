package octolendari.query

import octolendari.calendar.Calendar
import octolendari.calendar.event.CandidateEvent
import octolendari.util.createDateTime
import octolendari.util.createFakeGoogleCalendarConnector
import octolendari.util.createFakeTeamUpCalendarConnector
import org.joda.time.Interval
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OneToManyFreeOverlappingIntervalQueryTest {
    @Test
    fun `given google main calendar with google and teamUp comparable calendars, when query is executed, then list of EventCandidate is returned`() {
        val oneCalendar = Calendar(createFakeGoogleCalendarConnector(1), name = "main google calendar")
        val manyGoogleCalendar = Calendar(createFakeGoogleCalendarConnector(2), name = "google calendar")
        val manyTeamupCalendar = Calendar(createFakeTeamUpCalendarConnector(1), name = "teamup calendar")

        val query: Query = OneToManyFreeOverlappingIntervalQuery(
            oneCalendar,
            listOf(manyGoogleCalendar, manyTeamupCalendar),
            from = LocalDate.of(2022, 11, 20),
            to = LocalDate.of(2022, 11, 21)
        )
        val expectedCandidateEvents = arrayListOf(
            CandidateEvent(
                "google calendar",
                Interval(createDateTime(2022, 11, 20, 15, 0), createDateTime(2022, 11, 20, 16, 0))
            ),
            CandidateEvent(
                "google calendar",
                Interval(createDateTime(2022, 11, 20, 17, 0), createDateTime(2022, 11, 20, 18, 0))
            ),
            CandidateEvent(
                "google calendar",
                Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
            ),
            CandidateEvent(
                "google calendar",
                Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 17, 0))
            ),
            CandidateEvent(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 20, 13, 0), createDateTime(2022, 11, 20, 16, 0))
            ),
            CandidateEvent(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 20, 18, 0), createDateTime(2022, 11, 20, 18, 30))
            ),
            CandidateEvent(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
            ),
            CandidateEvent(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 18, 0))
            ),
        )
        val actualEventCandidates = query.doQuery()
        Assertions.assertEquals(expectedCandidateEvents,
            actualEventCandidates.sortedBy { it.calendarName })
    }
}