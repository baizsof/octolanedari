package calendari.util

import calendari.calendar.parser.FakeJsonWebCalendarParser
import calendari.calendar.configuration.CalendarConfiguration
import calendari.calendar.configuration.google.GooglePublicCalendarConfiguration
import calendari.calendar.connector.google.GooglePublicCalendarConnector
import calendari.calendar.configuration.TeamupCalendarConfiguration
import calendari.calendar.connector.TeamupCalendarConnector
import org.joda.time.DateTime
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.nio.file.Paths

val calendarTestResourcesPath: Path = Paths.get("src", "test", "resources", "calendar")

fun createFakeGoogleCalendarConfiguration(): GooglePublicCalendarConfiguration {
    return GooglePublicCalendarConfiguration(
        "test@gmail.com",
        "testApiKey",
        "Europe/Budapest"
    )
}

fun createFakeGoogleCalendarConnector(fakeSourceId: Int): GooglePublicCalendarConnector {
    val fakeCalendarParser = FakeJsonWebCalendarParser()
    val googlePublicCalendarConnector = GooglePublicCalendarConnector(
        createFakeGoogleCalendarConfiguration(),
        fakeCalendarParser
    )
    val rawJson = calendarTestResourcesPath.resolve(
        Paths.get(
            "google", "test-google-calendar-${fakeSourceId}.json"
        )
    ).toFile().inputStream()

    val jsonObject = JSONObject(String(rawJson.readBytes(), StandardCharsets.UTF_8))
    fakeCalendarParser.setReturnJSONObject(jsonObject)

    return googlePublicCalendarConnector
}

fun createFakeTeamUpCalendarConnector(fakeSourceId: Int) : TeamupCalendarConnector {
    val fakeCalendarParser = FakeJsonWebCalendarParser()
    val teamupCalendarConnector = TeamupCalendarConnector(
        createFakeTeamupCalendarConfiguration(),
        fakeCalendarParser
    )
    val rawJson = calendarTestResourcesPath.resolve(
        Paths.get(
            "teamup", "test-teamup-calendar-${fakeSourceId}.json"
        )
    ).toFile().inputStream()

    val jsonObject = JSONObject(String(rawJson.readBytes(), StandardCharsets.UTF_8))
    fakeCalendarParser.setReturnJSONObject(jsonObject)

    return teamupCalendarConnector
}

fun createFakeTeamupCalendarConfiguration(): CalendarConfiguration {
    return TeamupCalendarConfiguration("some-api-key", "Europe/Budapest")
}

fun createDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) =
    DateTime(year, month, day, hour, minute)
