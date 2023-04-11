package octolendari.calendar.event

import org.joda.time.Interval


class ReservedEvent(
    interval: Interval,
    calendar: String,
    val owner: String,
    val name: String,
) : Event(interval, calendar)
