package octolendari.calendar.configuration.teamup

import octolendari.calendar.configuration.CalendarConfigurationException
import octolendari.calendar.configuration.TeamupCalendarConfiguration
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URL
import java.nio.file.Paths
import java.util.Properties
import java.util.TimeZone

class TeamupCalendarConfigurationTest {
    private val generatedConfigurationFile = Paths.get("src", "test", "resources", "calendar", "configuration", "google-test-generated.calendar").toFile()

    @BeforeEach
    fun setUp() {
        generatedConfigurationFile.delete()
    }

    @Test
    fun `given google calendar configuration file contains calendar-id key, when getbaseurl called, then expected url is returned`() {
        val properties = Properties()
        properties["connector"] = "teamup"
        properties["time-zone"] = "Europe/Budapest"
        properties["calendar-id"] = "test-calendar-id"
        storeGeneratedProperties(properties)

        val configuration = TeamupCalendarConfiguration.fromFile(generatedConfigurationFile)

        val expectedUrl = URL("https://teamup.com/test-calendar-id/events?tz=Europe/Budapest")
        Assertions.assertEquals(expectedUrl, configuration.getBaseUrl())
    }

    @Test
    fun `given teamup calendar configuration file contains time-zone key, when getTimezone called, then expected TimeZone is returned`() {
        val properties = Properties()
        properties["connector"] = "teamup"
        properties["time-zone"] = "Europe/Budapest"
        properties["calendar-id"] = "test-calendar-id"
        storeGeneratedProperties(properties)

        val configuration = TeamupCalendarConfiguration.fromFile(generatedConfigurationFile)

        val expectedTimeZone = TimeZone.getTimeZone("Europe/Budapest")
        Assertions.assertEquals(expectedTimeZone, configuration.getTimezone())
    }

    @Test
    fun `given teamup calendar configuration file does not contain calendar-id key, when TeamupCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["connector"] = "teamup"
        properties["time-zone"] = "Europe/Budapest"
        storeGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            TeamupCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }


    @Test
    fun `given google calendar configuration file does not contain time-zone key, when TeamupCalendarConfiguration is created, then CalendarConfigurationException is thrown`() {
        val properties = Properties()
        properties["connector"] = "teamup"
        properties["calendar-id"] = "test-calendar-id"
        storeGeneratedProperties(properties)

        Assertions.assertThrows(CalendarConfigurationException::class.java) {
            TeamupCalendarConfiguration.fromFile(
                generatedConfigurationFile
            )
        }
    }


    fun storeGeneratedProperties(properties: Properties) {
        properties.store(generatedConfigurationFile.writer(), null)
    }


}