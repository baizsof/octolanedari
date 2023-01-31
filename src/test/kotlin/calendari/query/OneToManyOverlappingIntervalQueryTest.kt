package calendari.query

import calendari.calendar.Calendar
import calendari.calendar.EventCandidate
import calendari.util.createFakeGoogleCalendarConnector
import calendari.util.createFakeTeamUpCalendarConnector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.LocalDate

class OneToManyOverlappingIntervalQueryTest {
    @Disabled
    @Test
    fun `given google main calendar with google and teamUp comparable calendars, when query is executed, then list of EventCandidate is returned`() {
        val oneCalendar = Calendar(createFakeGoogleCalendarConnector(1), "main calendar")
        val manyGoogleCalendar = Calendar(createFakeGoogleCalendarConnector(2), "comparable google calendar")
        val manyTeamupCalendar = Calendar(createFakeTeamUpCalendarConnector(1), "comparable teamup calendar")

        val query: Query = OneToManyOverlappingIntervalQuery(
            oneCalendar,
            listOf(manyGoogleCalendar, manyTeamupCalendar),
            LocalDate.of(2022, 11, 20),
            LocalDate.of(2022, 11, 21)
        )
        //SpideyCalendar
        val expectedEventCandidates = arrayListOf<EventCandidate>()
        val actualEventCandidates = query.doQuery()
        Assertions.assertEquals(expectedEventCandidates, actualEventCandidates)
    }
}