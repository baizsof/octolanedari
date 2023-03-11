package calendari.calendar.configuration.google

import calendari.calendar.configuration.PublicCalendarConfiguration
import calendari.calendar.configuration.CalendarConfigurationException
import calendari.calendar.configuration.CalendarConfigurationException.Companion.API_KEY_REQUIRED
import calendari.calendar.configuration.CalendarConfigurationException.Companion.CALENDAR_ID_REQUIRED
import calendari.calendar.configuration.CalendarConfigurationException.Companion.TIME_ZONE_REQUIRED
import java.io.File
import java.net.URL
import java.util.Properties
import java.util.TimeZone

class GooglePublicCalendarConfiguration(
    private val calendarId: String,
    private val apiKey: String,
    private val timeZone: String
) : PublicCalendarConfiguration {

    override fun getBaseUrl(): URL {
        return URL("https://clients6.google.com/calendar/v3/calendars/$calendarId/events?&key=$apiKey")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }

    companion object {

        @JvmStatic
        fun fromFile(file: File): GooglePublicCalendarConfiguration {
            val properties = Properties()
            properties.load(file.reader())

            return fromProperties(properties)
        }
        @JvmStatic
        fun fromProperties(properties: Properties): GooglePublicCalendarConfiguration {
            val apiKey: String = properties.getProperty("api-key") ?: throw CalendarConfigurationException(
                API_KEY_REQUIRED
            )
            val calendarId: String = properties.getProperty("calendar-id") ?: throw CalendarConfigurationException(
                CALENDAR_ID_REQUIRED
            )
            val timeZone: String = properties.getProperty("time-zone") ?: throw CalendarConfigurationException(
                TIME_ZONE_REQUIRED
            )
            return GooglePublicCalendarConfiguration(
                calendarId,
                apiKey,
                timeZone
            )
        }
    }
}