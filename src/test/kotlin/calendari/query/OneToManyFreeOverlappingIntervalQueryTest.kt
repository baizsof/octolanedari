package calendari.query

import calendari.calendar.Calendar
import calendari.calendar.EventCandidate
import calendari.util.createDateTime
import calendari.util.createFakeGoogleCalendarConnector
import calendari.util.createFakeTeamUpCalendarConnector
import org.joda.time.Interval
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OneToManyFreeOverlappingIntervalQueryTest {
    @Test
    fun `given google main calendar with google and teamUp comparable calendars, when query is executed, then list of EventCandidate is returned`() {
        val oneCalendar = Calendar(createFakeGoogleCalendarConnector(1), "main google calendar")
        val manyGoogleCalendar = Calendar(createFakeGoogleCalendarConnector(2), "google calendar")
        val manyTeamupCalendar = Calendar(createFakeTeamUpCalendarConnector(1), "teamup calendar")

        val query: Query = OneToManyFreeOverlappingIntervalQuery(
            oneCalendar,
            listOf(manyGoogleCalendar, manyTeamupCalendar),
            from = LocalDate.of(2022, 11, 20),
            to = LocalDate.of(2022, 11, 21)
        )
        val expectedEventCandidates = arrayListOf(
            EventCandidate(
                "google calendar",
                Interval(createDateTime(2022, 11, 20, 15, 0), createDateTime(2022, 11, 20, 16, 0))
            ),
            EventCandidate(
                "google calendar",
                Interval(createDateTime(2022, 11, 20, 17, 0), createDateTime(2022, 11, 20, 18, 0))
            ),
            EventCandidate(
                "google calendar",
                Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
            ),
            EventCandidate(
                "google calendar",
                Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 17, 0))
            ),
            EventCandidate(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 20, 13, 0), createDateTime(2022, 11, 20, 16, 0))
            ),
            EventCandidate(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 20, 18, 0), createDateTime(2022, 11, 20, 18, 30))
            ),
            EventCandidate(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
            ),
            EventCandidate(
                "teamup calendar",
                Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 18, 0))
            ),
        )
        val actualEventCandidates = query.doQuery()
        Assertions.assertEquals(expectedEventCandidates,
            actualEventCandidates.sortedBy { it.calendarName })
    }
}