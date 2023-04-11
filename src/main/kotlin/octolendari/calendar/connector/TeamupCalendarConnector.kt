package octolendari.calendar.connector

import octolendari.calendar.configuration.PublicCalendarConfiguration
import octolendari.calendar.event.Event
import octolendari.calendar.event.ReservedEvent
import octolendari.calendar.mapper.EventMapper
import octolendari.calendar.parser.JsonWebCalendarParser
import octolendari.calendar.mapper.TeamUpEventMapper
import org.apache.http.client.utils.URIBuilder
import org.json.JSONObject
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TeamupCalendarConnector(
    private val configuration : PublicCalendarConfiguration,
    private val parser : JsonWebCalendarParser = JsonWebCalendarParser(),
    private val mapper : EventMapper<JSONObject> = TeamUpEventMapper()
) : CalendarConnector {
    private val dateFormatUsedByTeamUp: DateTimeFormatter = DateTimeFormatter.ISO_DATE
    override fun getEvents(from: LocalDate, to: LocalDate): List<Event> {
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

    private fun mapEvents(queryResult: JSONObject): List<ReservedEvent> {
        val reservedEvents: ArrayList<ReservedEvent> = arrayListOf()
        for (rawEvent in queryResult.getJSONArray("events")) {
            reservedEvents.add(mapper.getEvent(rawEvent as JSONObject))
        }
        return reservedEvents

    }

}