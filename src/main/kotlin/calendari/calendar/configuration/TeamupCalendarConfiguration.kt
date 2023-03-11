package calendari.calendar.configuration

import calendari.calendar.configuration.CalendarConfigurationException.Companion.CALENDAR_ID_REQUIRED
import calendari.calendar.configuration.CalendarConfigurationException.Companion.TIME_ZONE_REQUIRED
import java.io.File
import java.net.URL
import java.util.Properties
import java.util.TimeZone

class TeamupCalendarConfiguration(private val calendarId: String, private val timeZone: String) :
    PublicCalendarConfiguration {
    override fun getBaseUrl(): URL {
        return URL("https://teamup.com/$calendarId/events?tz=${timeZone}")
    }

    override fun getTimezone(): TimeZone {
        return TimeZone.getTimeZone(timeZone)
    }

    companion object {
        fun fromFile(file: File): TeamupCalendarConfiguration {
            val properties = Properties()
            properties.load(file.reader())
            return fromProperties(properties)
        }
        @JvmStatic
        fun fromProperties(properties: Properties): TeamupCalendarConfiguration {
            val calendarId: String = properties.getProperty("calendar-id") ?: throw CalendarConfigurationException(
                CALENDAR_ID_REQUIRED
            )
            val timeZone: String = properties.getProperty("time-zone") ?: throw CalendarConfigurationException(
                TIME_ZONE_REQUIRED
            )
            return TeamupCalendarConfiguration(
                calendarId,
                timeZone
            )
        }
    }
}