package octolendari.presenter

import octolendari.calendar.event.CandidateEvent
import octolendari.util.createDateTime
import org.joda.time.Interval
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class TxtFileQueryPresenterTest {

    private val candidateEvents = arrayListOf(
        CandidateEvent(
            "google calendar", Interval(createDateTime(2022, 11, 20, 15, 0), createDateTime(2022, 11, 20, 16, 0))
        ),
        CandidateEvent(
            "google calendar", Interval(createDateTime(2022, 11, 20, 17, 0), createDateTime(2022, 11, 20, 18, 0))
        ),
        CandidateEvent(
            "google calendar", Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
        ),
        CandidateEvent(
            "google calendar", Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 17, 0))
        ),
        CandidateEvent(
            "teamup calendar", Interval(createDateTime(2022, 11, 20, 13, 0), createDateTime(2022, 11, 20, 16, 0))
        ),
        CandidateEvent(
            "teamup calendar", Interval(createDateTime(2022, 11, 20, 18, 0), createDateTime(2022, 11, 20, 18, 30))
        ),
        CandidateEvent(
            "teamup calendar", Interval(createDateTime(2022, 11, 21, 8, 0), createDateTime(2022, 11, 21, 9, 0))
        ),
        CandidateEvent(
            "teamup calendar", Interval(createDateTime(2022, 11, 21, 10, 0), createDateTime(2022, 11, 21, 18, 0))
        ),
    )

    @Test
    fun `given list of event candidates, when present called, then event candidates file equals with expected query file`() {
        val presenterTestFolder = Paths.get("src", "test", "resources", "presenter")
        val actualFile = presenterTestFolder.resolve("actual-query.txt").toFile()
        val expectedFile = presenterTestFolder.resolve("expected-query.txt").toFile()
        val presenter = TxtFileQueryPresenter(actualFile)
        presenter.present(candidateEvents)
        Assertions.assertEquals(expectedFile.readText() ,actualFile.readText())
    }
}