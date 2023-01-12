package calendari.calendar

import org.joda.time.Interval

data class EventCandidate(
    val calendarName: String,
    val interval: Interval,
)
