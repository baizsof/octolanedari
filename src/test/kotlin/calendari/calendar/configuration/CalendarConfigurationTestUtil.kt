package calendari.calendar.configuration

import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

val calendarConfigurationResourcesFolder: Path =
    Paths.get("src", "test", "resources", "calendar", "configuration")

fun getTestCalendarConfigurationFile(connector: String): File {
    return calendarConfigurationResourcesFolder.resolve("$connector-test.calendar").toFile()
}