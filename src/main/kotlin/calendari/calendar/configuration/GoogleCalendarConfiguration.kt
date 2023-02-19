package calendari.calendar.configuration

import calendari.calendar.configuration.CalendarConfigurationException.Companion.API_KEY_REQUIRED
import calendari.calendar.configuration.CalendarConfigurationException.Companion.CALENDAR_ID_REQUIRED
import calendari.calendar.configuration.CalendarConfigurationException.Companion.TIME_ZONE_REQUIRED
import java.io.File
import java.net.URL
import java.util.Properties
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
        fun fromFile(file: File): GoogleCalendarConfiguration {
            val properties = Properties()
            properties.load(file.reader())

            return fromProperties(properties)
        }
        @JvmStatic
        fun fromProperties(properties: Properties): GoogleCalendarConfiguration {
            val apiKey: String = properties.getProperty("api-key") ?: throw CalendarConfigurationException(
                API_KEY_REQUIRED
            )
            val calendarId: String = properties.getProperty("calendar-id") ?: throw CalendarConfigurationException(
                CALENDAR_ID_REQUIRED
            )
            val timeZone: String = properties.getProperty("time-zone") ?: throw CalendarConfigurationException(
                TIME_ZONE_REQUIRED
            )
            return GoogleCalendarConfiguration(
                calendarId,
                apiKey,
                timeZone
            )
        }
    }
}