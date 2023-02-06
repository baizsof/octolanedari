package calendari.presenter

import calendari.calendar.EventCandidate
import java.io.File

class TxtFileQueryPresenter : QueryPresenter {
    private val file = File("query.txt")

    override fun present(candidates: ArrayList<EventCandidate>) {
        file.writeText("Query result\n\n")
        val eventCandidatesGroupedByCalendarNameSortedByStart = candidates.sortedBy{ it.interval.start }.groupBy { it.calendarName }
        for ((calendarName, eventCandidates) in eventCandidatesGroupedByCalendarNameSortedByStart.entries) {
            file.appendText("$calendarName\n")
            for (evenCandidate in eventCandidates) {
                val userFriendlyIntervalFormat : String = if(evenCandidate.interval.start.toLocalDate().equals(evenCandidate.interval.end.toLocalDate())) {
                    "${evenCandidate.interval.start.toString("yyyy-MM-dd")} ${evenCandidate.interval.start.toString("HH:mm")}-${evenCandidate.interval.end.toString("HH:mm")}"
                } else {
                    "${evenCandidate.interval.start.toString("yyyy-MM-dd HH:mm")} ${evenCandidate.interval.end.toString("yyyy-MM-dd HH:mm")} "
                }
                file.appendText("$userFriendlyIntervalFormat\n")
            }
            file.appendText("\n")
        }

    }
}