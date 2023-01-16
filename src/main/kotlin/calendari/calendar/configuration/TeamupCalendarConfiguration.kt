package calendari.calendar.configuration

import java.net.URL
import java.util.TimeZone

class TeamupCalendarConfiguration(private val apiKey: String, private val timeZone: String) : CalendarConfiguration {
    override fun getBaseUrl(): URL {
        return URL("https://teamup.com/$apiKey/events?tz=${timeZone}")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }
}