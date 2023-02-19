package calendari.calendar.mapper

import calendari.calendar.Event

interface EventMapper<T> {

    fun getEvent(rawEvent: T): Event

}