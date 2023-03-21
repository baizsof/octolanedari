package octolendari.calendar.mapper

import octolendari.calendar.Event

interface EventMapper<T> {

    fun getEvent(rawEvent: T): Event

}