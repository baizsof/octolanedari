package octolendari.calendar.configuration.google

import octolendari.calendar.configuration.CalendarConfigurationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL
import java.nio.file.Paths
import java.util.Properties
import java.util.TimeZone

class GooglePublicCalendarConfigurationTest {
    private val generatedConfigurationFile = Paths.get("src", "test", "resources", "calendar", "configuration", "google-test-generated.calendar").toFile()

    @BeforeEach
    fun setUp() {
        generatedConfigurationFile.delete()
    }

    @Test
    fun `given google calendar configuration file contains calendar-id key, when getBaseUrl called, then expected URL is returned`() {
        val properties = Properties()
        properties["connector"] = "google"
        properties["calendar-id"] = "test@gmail.com"
        properties["api-key"] = "test-api-key"
        properties["time-zone"] = "Europe/Budapest"
        storeGeneratedProperties(properties)
        val configuration = GooglePublicCalendarConfiguration.fromFile(generatedConfigurationFile)

        val expectedUrl = URL("https://clients6.google.com/calendar/v3/calendars/test@gmail.com/events?&key=test-api-key")
        Assertions.assertEquals(expectedUrl, configuration.getBaseUrl())
    }

    @Test
    fun `given google calendar configuration file contains time-zone key, when getTimezone called, then expected TimeZone is returned`() {
        val properties = Properties()
        properties["connector"] = "google"
        properties["calendar-id"] = "test@gmail.com"
        properties["api-key"] = "test-api-key"
        properties["time-zone"] = "Europe/Budapest"
        storeGeneratedProperties(properties)
        val configuration = GooglePublicCalendarConfiguration.fromFile(generatedConfigurationFile)

        val expectedTimeZone = TimeZone.getTimeZone("Europe/Budapest")
        Assertions.assertEquals(expectedTimeZone, configuration.getTimezone())
    }

    @Test
    fun `given google calendar configuration file does not contain calendar-id key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["connector"] = "google"
        properties["api-key"] = "test-api-key"
        properties["time-zone"] = "Europe/Budapest"
        storeGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GooglePublicCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }

    @Test
    fun `given google calendar configuration file does not contain api-key key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["connector"] = "google"
        properties["calendar-id"] = "test@gmail.com"
        properties["time-zone"] = "Europe/Budapest"
        storeGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GooglePublicCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }

    @Test
    fun `given google calendar configuration file does not contain time-zone key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["connector"] = "google"
        properties["calendar-id"] = "test@gmail.com"
        properties["api-key"] = "test-api-key"
        storeGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GooglePublicCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }


    fun storeGeneratedProperties(properties: Properties) {
        properties.store(generatedConfigurationFile.writer(), null)
    }


}