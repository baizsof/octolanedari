package calendari.calendar

import org.joda.time.Interval


data class Event(
    val interval: Interval,
    val owner: String = "",
    val name: String = "",
    val location: String = "",
    val isBusy: Boolean = true
)
