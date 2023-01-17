package calendari.calendar.configuration.google

import calendari.calendar.configuration.CalendarConfigurationException
import calendari.calendar.configuration.GoogleCalendarConfiguration
import calendari.calendar.configuration.calendarConfigurationResourcesFolder
import calendari.calendar.configuration.getTestCalendarConfigurationFile
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL
import java.util.Properties
import java.util.TimeZone

class GoogleCalendarConfigurationTest {
    private val generatedConfigurationFile = calendarConfigurationResourcesFolder.resolve("google-test-generated.calendar").toFile()

    @BeforeEach
    fun setUp() {
        generatedConfigurationFile.delete()
    }

    @Test
    fun `given google calendar configuration file contains calendar-id key, when getBaseUrl called, then expected URL is returned`() {
        val validConfigurationFile = getTestCalendarConfigurationFile("google")
        val configuration = GoogleCalendarConfiguration.fromFile(validConfigurationFile)

        val expectedUrl = URL("https://clients6.google.com/calendar/v3/calendars/test1@gmail.com/events?&key=test1-api-key")
        Assertions.assertEquals(expectedUrl, configuration.getBaseUrl())
    }

    @Test
    fun `given google calendar configuration file contains time-zone key, when getTimezone called, then expected TimeZone is returned`() {
        val validConfigurationFile = getTestCalendarConfigurationFile("google")
        val configuration = GoogleCalendarConfiguration.fromFile(validConfigurationFile)

        val expectedTimeZone = TimeZone.getTimeZone("Europe/Budapest")
        Assertions.assertEquals(expectedTimeZone, configuration.getTimezone())
    }

    @Test
    fun `given google calendar configuration file does not contain calendar-id key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["api-key"] = "test-api-key"
        properties["time-zone"] = "Europe/Budapest"
        saveGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GoogleCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }

    @Test
    fun `given google calendar configuration file does not contain api-key key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["calendar-id"] = "test@gmail.com"
        properties["time-zone"] = "Europe/Budapest"
        saveGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GoogleCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }

    @Test
    fun `given google calendar configuration file does not contain time-zone key, when GoogleCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["calendar-id"] = "test@gmail.com"
        properties["api-key"] = "test-api-key"
        saveGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            GoogleCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }


    fun saveGeneratedProperties(properties: Properties) {
        properties.store(generatedConfigurationFile.writer(), null)
    }


}