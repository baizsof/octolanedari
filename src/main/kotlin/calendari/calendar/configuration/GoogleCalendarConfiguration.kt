package calendari.calendar.configuration

import java.net.URL
import java.util.TimeZone

class GoogleCalendarConfiguration(
    private val id: String,
    private val apiKey: String,
    private val timeZone: String
) : CalendarConfiguration {
    override fun getBaseUrl(): URL {
        return URL("https://clients6.google.com/calendar/v3/calendars/$id/events?&key=$apiKey")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }
}