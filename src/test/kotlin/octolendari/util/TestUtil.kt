package octolendari.util

import octolendari.calendar.parser.FakeJsonWebCalendarParser
import octolendari.calendar.configuration.PublicCalendarConfiguration
import octolendari.calendar.configuration.google.GooglePublicCalendarConfiguration
import octolendari.calendar.connector.google.GooglePublicCalendarConnector
import octolendari.calendar.configuration.TeamupCalendarConfiguration
import octolendari.calendar.connector.TeamupCalendarConnector
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

fun createFakeTeamupCalendarConfiguration(): PublicCalendarConfiguration {
    return TeamupCalendarConfiguration("some-api-key", "Europe/Budapest")
}

fun createDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int) =
    DateTime(year, month, day, hour, minute)
