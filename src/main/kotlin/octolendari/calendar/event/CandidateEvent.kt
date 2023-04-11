package octolendari.calendar.event

import org.joda.time.Interval

data class CandidateEvent(
    val calendarName: String,
    val interval: Interval,
)
