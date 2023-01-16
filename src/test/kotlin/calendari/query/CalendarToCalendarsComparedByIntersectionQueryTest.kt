package calendari.query

import calendari.calendar.Calendar
import calendari.calendar.EventCandidate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import calendari.util.createFakeGoogleCalendarConnector
import calendari.util.createFakeTeamUpCalendarConnector
import java.time.LocalDate

class CalendarToCalendarsComparedByIntersectionQueryTest {
    @Test
    fun `given google main calendar with google and teamUp comparable calendars, when query is executed, then list of EventCandidate is returned`() {
        val mainCalendar = Calendar(createFakeGoogleCalendarConnector(1), "main calendar")
        val comparableGoogleCalendar = Calendar(createFakeGoogleCalendarConnector(2), "comparable google calendar")
        val comparableTeamUpCalendar = Calendar(createFakeTeamUpCalendarConnector(1), "comparable teamup calendar")

        val query: Query = CalendarToCalendarsComparedByIntersectionQuery(
            mainCalendar,
            listOf(comparableGoogleCalendar, comparableTeamUpCalendar),
            LocalDate.of(2022, 11, 20),
            LocalDate.of(2022, 11, 21)
        )
        //SpideyCalendar
        val expectedEventCandidates = arrayListOf<EventCandidate>()
        val actualEventCandidates = query.doQuery()
        Assertions.assertEquals(expectedEventCandidates, actualEventCandidates)
    }
}