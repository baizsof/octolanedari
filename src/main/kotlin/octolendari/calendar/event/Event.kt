package octolendari.calendar.event

import org.joda.time.Interval

abstract class Event(
    val interval: Interval,
    val calendar: String,
)
