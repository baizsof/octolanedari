package calendari.query

import calendari.calendar.EventCandidate

interface Query {
    fun doQuery() : List<EventCandidate>
}