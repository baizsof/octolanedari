package calendari.calendar.configuration

import java.io.File
import java.net.URL
import java.util.TimeZone

class GoogleCalendarConfiguration(
    private val calendarId: String,
    private val apiKey: String,
    private val timeZone: String
) : CalendarConfiguration {

    override fun getBaseUrl(): URL {
        return URL("https://clients6.google.com/calendar/v3/calendars/$calendarId/events?&key=$apiKey")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }

    companion object {

        @JvmStatic
        fun fromFile(file : File) : GoogleCalendarConfiguration {
            throw NotImplementedError()
        }
    }
}