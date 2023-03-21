package octolendari.calendar.parser

import java.net.URL

interface WebCalendarParser<T> {

    fun parse() : T

    fun setUrl(url: URL)

}
