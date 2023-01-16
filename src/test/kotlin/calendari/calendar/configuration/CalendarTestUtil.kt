package configuration

import java.io.File
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties
import kotlin.io.path.writer

val googleCalendarConfigurationResourcesFolder: Path = Paths.get("src", "test", "resources", "configuration")

fun createCalendarConfigurationFile(calendarId : String, apikey : String, timeZone : String) {
    val file = googleCalendarConfigurationResourcesFolder.resolve("test-google.configuration")
    val properties = Properties()
    properties["calendar-id"] = calendarId
    properties["api-key"] = apikey
    properties["time-zone"]= timeZone

    properties.store(file.writer(Charset.defaultCharset()), null)
}

fun getGoogleCalendarConfigurationFile(id : Int) : File {
    return googleCalendarConfigurationResourcesFolder.resolve("google-test-1.calendar").toFile()
}