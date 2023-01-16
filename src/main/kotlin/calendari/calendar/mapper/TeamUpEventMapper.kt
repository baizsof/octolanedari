package calendari.calendar.mapper

import calendari.calendar.Event
import org.joda.time.Interval
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TeamUpEventMapper : EventMapper<JSONObject> {
    override fun getEvent(rawEvent: JSONObject): Event {
        val start = LocalDateTime.parse(rawEvent.getString("start_dt"), DateTimeFormatter.ISO_DATE_TIME)
        val end = LocalDateTime.parse(rawEvent.getString("end_dt"), DateTimeFormatter.ISO_DATE_TIME)
        return Event(
            owner = rawEvent.getString("title")!!,
            interval = Interval(
                start.atZone(ZoneId.systemDefault()).toEpochSecond(),
                end.atZone(ZoneId.systemDefault()).toEpochSecond()
            )
        )
    }
}