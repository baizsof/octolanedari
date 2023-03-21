package octolendari.presenter

import octolendari.calendar.EventCandidate

interface QueryPresenter {
    fun present(candidates: List<EventCandidate>)
}