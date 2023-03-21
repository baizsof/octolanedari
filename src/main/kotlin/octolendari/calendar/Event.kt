package octolendari.calendar

import org.joda.time.Interval


data class Event(
    val interval: Interval,
    val owner: String = "",
    val name: String = "",
    val calendar: String = "",
)
