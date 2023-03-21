package octolendari.calendar.interval.searcher

import org.joda.time.Interval

interface IntervalSearcher {

    fun search(searchInterval: Interval, intervals: List<Interval>): List<Interval>
}