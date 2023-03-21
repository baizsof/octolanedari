package octolendari.query

import octolendari.calendar.EventCandidate

interface Query {
    fun doQuery() : List<EventCandidate>
}