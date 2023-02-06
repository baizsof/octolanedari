package calendari.presenter

import calendari.calendar.EventCandidate
import calendari.util.createDateTime
import org.joda.time.Interval
import org.junit.jupiter.api.Test

class TxtFileQueryPresenterTest {

    private val eventCandidates = arrayListOf(
        EventCandidate(
            "google calendar", Interval(createDateTime(2022, 11, 20, 15, 0), createDateTime(2022, 11, 20, 16, 0))
        ),
        EventCandidate(
            "google calendar", Interval(createDateTime(2022, 11, 20, 17, 0), createDateTime(2022, 11, 20, 18, 0))
        ),
        EventCandidate(
            "google calendar", Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
        ),
        EventCandidate(
            "google calendar", Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 17, 0))
        ),
        EventCandidate(
            "teamup calendar", Interval(createDateTime(2022, 11, 20, 13, 0), createDateTime(2022, 11, 20, 16, 0))
        ),
        EventCandidate(
            "teamup calendar", Interval(createDateTime(2022, 11, 20, 18, 0), createDateTime(2022, 11, 20, 18, 30))
        ),
        EventCandidate(
            "teamup calendar", Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
        ),
        EventCandidate(
            "teamup calendar", Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 18, 0))
        ),
    )

    @Test
    fun name() {
        val presenter = TxtFileQueryPresenter()
        presenter.present(eventCandidates)
    }
}