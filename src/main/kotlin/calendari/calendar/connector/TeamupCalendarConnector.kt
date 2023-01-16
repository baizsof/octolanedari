package calendari.calendar.connector

import calendari.calendar.configuration.CalendarConfiguration
import calendari.calendar.Event
import calendari.calendar.mapper.EventMapper
import calendari.calendar.parser.UrlJsonCalendarParser
import calendari.calendar.mapper.TeamUpEventMapper
import org.apache.http.client.utils.URIBuilder
import org.json.JSONObject
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TeamupCalendarConnector(
    private val configuration : CalendarConfiguration,
    private val parser : UrlJsonCalendarParser = UrlJsonCalendarParser(),
    private val mapper : EventMapper<JSONObject> = TeamUpEventMapper()
) : CalendarConnector {
    private val dateFormatUsedByTeamUp: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    override fun getEvents(from: LocalDate, to: LocalDate) : List<Event> {
        val queryURL = createURL(from, to)
        parser.setUrl(queryURL)

        val queryResult : JSONObject = parser.parse()

        return mapEvents(queryResult)
    }

    private fun createURL(from: LocalDate, to: LocalDate): URL {
        //TODO: audit this third party lib
        return URIBuilder(configuration.getBaseUrl().toString())
            .addParameter("startDate", from.format(dateFormatUsedByTeamUp))
            .addParameter("endDate", to.format(dateFormatUsedByTeamUp))
            .build().toURL()
    }

    private fun mapEvents(queryResult: JSONObject): List<Event> {
        val events: ArrayList<Event> = arrayListOf()
        for (rawEvent in queryResult.getJSONArray("events")) {
            events.add(mapper.getEvent(rawEvent as JSONObject))
        }
        return events

    }

}