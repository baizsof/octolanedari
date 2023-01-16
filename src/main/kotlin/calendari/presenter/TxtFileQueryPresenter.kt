package calendari.presenter

import calendari.calendar.EventCandidate
import java.io.File

class TxtFileQueryPresenter : QueryPresenter {
    val file = File("query.txt")

    override fun present(candidates: ArrayList<EventCandidate>) {
        TODO("Not yet implemented")
    }
}