package calendari.calendar.configuration

import java.io.File
import java.net.URL
import java.util.TimeZone

class TeamupCalendarConfiguration(private val calendarId: String, private val timeZone: String) : CalendarConfiguration {
    override fun getBaseUrl(): URL {
        return URL("https://teamup.com/$calendarId/events?tz=${timeZone}")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }

    companion object {
        fun fromFile(file1: File) : TeamupCalendarConfiguration {
            throw NotImplementedError()
        }
    }
}