package octolendari.calendar.configuration

import java.net.URL
import java.util.TimeZone

interface PublicCalendarConfiguration {
    fun getBaseUrl(): URL
    fun getTimezone(): TimeZone
}
