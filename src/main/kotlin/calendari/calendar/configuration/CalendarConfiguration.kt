package calendari.calendar.configuration

import java.net.URL
import java.util.TimeZone

interface CalendarConfiguration {
    fun getBaseUrl(): URL
    fun getTimezone(): TimeZone
}
