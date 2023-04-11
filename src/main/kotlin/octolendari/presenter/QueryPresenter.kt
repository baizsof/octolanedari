package octolendari.presenter

import octolendari.calendar.event.CandidateEvent

interface QueryPresenter {
    fun present(candidates: List<CandidateEvent>)
}