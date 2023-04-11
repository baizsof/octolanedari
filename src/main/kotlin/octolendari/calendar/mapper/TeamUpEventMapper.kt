package octolendari.calendar.mapper

import octolendari.calendar.event.ReservedEvent
import org.joda.time.DateTime
import org.joda.time.Interval
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TeamUpEventMapper : EventMapper<JSONObject> {
    override fun getEvent(rawEvent: JSONObject): ReservedEvent {
        val start = LocalDateTime.parse(rawEvent.getString("start_dt"), DateTimeFormatter.ISO_DATE_TIME)
        val end = LocalDateTime.parse(rawEvent.getString("end_dt"), DateTimeFormatter.ISO_DATE_TIME)
        return ReservedEvent(
            owner = rawEvent.getString("title")!!,
            interval = Interval(
                DateTime(start.year, start.monthValue, start.dayOfMonth, start.hour, start.minute),
                DateTime(end.year, end.monthValue, end.dayOfMonth, end.hour, end.minute)
            ),
            calendar = "teamup",
            name = "name"
        )
    }
}