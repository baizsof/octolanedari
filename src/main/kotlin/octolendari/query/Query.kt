package octolendari.query

import octolendari.calendar.event.CandidateEvent

interface Query {
    fun doQuery() : List<CandidateEvent>
}