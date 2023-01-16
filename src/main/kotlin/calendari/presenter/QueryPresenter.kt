package calendari.presenter

import calendari.calendar.EventCandidate

interface QueryPresenter {
    fun present(candidates: ArrayList<EventCandidate>)
}