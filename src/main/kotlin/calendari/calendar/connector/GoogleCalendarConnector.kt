package calendari.calendar.connector

import calendari.calendar.configuration.CalendarConfiguration
import calendari.calendar.Event
import calendari.calendar.mapper.EventMapper
import calendari.calendar.mapper.GoogleEventMapper
import calendari.calendar.parser.JsonCalendarParser
import calendari.calendar.parser.UrlJsonCalendarParser
import org.apache.http.client.utils.URIBuilder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import org.json.JSONObject
import java.net.URL
import java.time.LocalDate


class GoogleCalendarConnector(
    private val configuration : CalendarConfiguration,
    private val parser : JsonCalendarParser = UrlJsonCalendarParser(),
    private val mapper : EventMapper<JSONObject> = GoogleEventMapper()
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