package calendari.calendar.mapper.google

import calendari.calendar.Event
import calendari.calendar.mapper.EventMapper
import org.joda.time.DateTime
import org.joda.time.Interval
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GooglePublicApiEventMapper : EventMapper<JSONObject> {
    override fun getEvent(rawEvent: JSONObject): Event {
        val start = LocalDateTime.parse(rawEvent.getJSONObject("start").getString("dateTime"), DateTimeFormatter.ISO_DATE_TIME)
        val end = LocalDateTime.parse(rawEvent.getJSONObject("end").getString("dateTime"), DateTimeFormatter.ISO_DATE_TIME)
        return Event(
            interval = Interval(
                DateTime(start.year, start.monthValue, start.dayOfMonth, start.hour, start.minute),
                DateTime(end.year, end.monthValue, end.dayOfMonth, end.hour, end.minute)
            )
        )

    }
}