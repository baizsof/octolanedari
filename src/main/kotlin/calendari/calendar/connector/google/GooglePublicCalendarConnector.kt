package calendari.calendar.connector.google

import calendari.calendar.configuration.CalendarConfiguration
import calendari.calendar.Event
import calendari.calendar.connector.CalendarConnector
import calendari.calendar.mapper.EventMapper
import calendari.calendar.mapper.google.GooglePublicApiEventMapper
import calendari.calendar.parser.WebCalendarParser
import calendari.calendar.parser.JsonWebCalendarParser
import org.apache.http.client.utils.URIBuilder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import org.json.JSONObject
import java.net.URL
import java.time.LocalDate


class GooglePublicCalendarConnector(
    private val configuration : CalendarConfiguration,
    private val parser : WebCalendarParser<JSONObject> = JsonWebCalendarParser(),
    private val mapper : EventMapper<JSONObject> = GooglePublicApiEventMapper()
) : CalendarConnector {
    private val dateFormatUsedByGoogle: DateTimeFormatter = ISODateTimeFormat.dateTime()
    override fun getEvents(from: LocalDate, to: LocalDate) : List<Event> {
        val queryURL = createURL(from, to)
        parser.setUrl(queryURL)

        val queryResult : JSONObject = parser.parse()

        return mapEvents(queryResult)
    }

    private fun createURL(from: LocalDate, to: LocalDate): URL {
        val min = DateTime(from.year, from.monthValue, from.dayOfMonth, 0, 0)
        val max = DateTime(to.year, to.monthValue, to.dayOfMonth, 0, 0)
        return URIBuilder(configuration.getBaseUrl().toString())
            .addParameter("timeZone", configuration.getTimezone().toString())
            .addParameter("timeMin", dateFormatUsedByGoogle.print(min))
            .addParameter("timeMax", dateFormatUsedByGoogle.print(max))
            .build().toURL()
    }

    private fun mapEvents(queryResult: JSONObject): List<Event> {
        val events: ArrayList<Event> = arrayListOf()
        for (rawEvent in queryResult.getJSONArray("items")) {
            events.add(mapper.getEvent(rawEvent as JSONObject))
        }
        return events

    }
}