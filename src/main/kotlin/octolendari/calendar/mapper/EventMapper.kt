package octolendari.calendar.mapper

import octolendari.calendar.event.ReservedEvent

interface EventMapper<T> {

    fun getEvent(rawEvent: T): ReservedEvent

}