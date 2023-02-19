package calendari.calendar.interval.searcher

import org.joda.time.Interval


class FreeIntervalsSearcher : IntervalSearcher {

    override fun search(searchInterval: Interval, intervals: List<Interval>): List<Interval> {
        if (intervals.isEmpty()) {
            listOf(searchInterval)
        }
        val overlappingIntervals = intervals.filter { interval -> interval.overlaps(searchInterval) }
        if (overlappingIntervals.isEmpty()) {
            return listOf(searchInterval)
        }

        val gaps = arrayListOf<Interval>()

        val firstGap : Interval? = Interval(searchInterval.start - 1, searchInterval.start).gap(overlappingIntervals.first())
        firstGap?.let{gaps.add(it)}

        for (i in 0 until overlappingIntervals.lastIndex) {
            val current = overlappingIntervals[i]
            val next = overlappingIntervals[i + 1]
            val gap : Interval? = current.gap(next)
            gap?.let{gaps.add(it)}
        }
        val lastGap : Interval? = Interval(searchInterval.end, searchInterval.end + 1).gap(overlappingIntervals.last())
        lastGap?.let{gaps.add(it)}

        return gaps
    }
}