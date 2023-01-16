package calendari.calendar.interval.searcher

import org.joda.time.DateTime
import org.joda.time.Interval
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals


class GapIntervalSearcherTest {
    private val gapIntervalSearcher: GapIntervalSearcher = GapIntervalSearcher()

    /*
    search interval -----------|xxxxxxxx|-
    intervals       ----------------------

    expected        -----------|xxxxxxxx|-
     */
    @Test
    fun `given search interval without intervals, when search executed, then search interval is returned`() {
        val searchIntervals = createInterval(startHour = 11, endHour = 12)
        assertEquals(listOf(searchIntervals), gapIntervalSearcher.search(searchIntervals, emptyList()))
    }

    /*
    search interval -----------|xxxxxxxx|-
    intervals       --|xx|-|xx|-----------

    expected        -----------|xxxxxxxx|-
     */
    @Test
    fun `given search interval with not overlapping intervals, when intervals are before search interval and search executed, then search interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 9, endHour = 10)
        )

        val searchIntervals = createInterval(startHour = 11, endHour = 12)
        assertEquals(listOf(searchIntervals), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval --|xxxxxxxx|-----------
    intervals       -------------|xx|-|xx|-

    expected        --|xxxxxxxx|-----------
     */
    @Test
    fun `given search interval with not overlapping intervals, when intervals are after search interval and search executed, then search interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 9, endHour = 10)
        )

        val searchIntervals = createInterval(startHour = 6, endHour = 7)
        assertEquals(listOf(searchIntervals), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval --------|xxxxxxxx|-------
    intervals       --|xx|--------------|xx|-

    expected        --------|xxxxxxxx|-------
     */
    @Test
    fun `given search interval with not overlapping intervals, when intervals are both before and after search interval and search executed, then search interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 9, endHour = 10)
        )

        val searchIntervals = createInterval(startHour = 6, endHour = 7)
        assertEquals(listOf(searchIntervals), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval --------|xxxxxxxx|-------
    intervals       --|xx|--|xxx|------------

    expected        ------------|xxxx|-------

     */
    @Test
    fun `given not overlapping interval before search interval, when overlapping interval starts when search interval does and search executed, then expected interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 10, endHour = 12)
        )

        val searchIntervals = createInterval(startHour = 10, endHour = 14)
        val expectedInterval = createInterval(startHour = 12, endHour = 14)
        assertEquals(listOf(expectedInterval), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval ---------|xxxxxxxx|-------
    intervals       --|xx|-|xxxxx|------------

    expected        -------------|xxxx|-------

     */
    @Test
    fun `given not overlapping interval before search interval, when overlapping interval starts before search interval does and search executed, then expected interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 9, endHour = 12)
        )

        val searchIntervals = createInterval(startHour = 10, endHour = 14)
        val expectedInterval = createInterval(startHour = 12, endHour = 14)
        assertEquals(listOf(expectedInterval), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval ---------|xxxxxxxx|-------
    intervals       --|xx|--------|xxx|-------

    expected        ---------|xxxx|-----------

     */
    @Test
    fun `given not overlapping interval before search interval, when overlapping interval ends when search interval does and search executed, then expected interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 13, endHour = 14)
        )

        val searchIntervals = createInterval(startHour = 10, endHour = 14)
        val expectedInterval = createInterval(startHour = 10, endHour = 13)
        assertEquals(listOf(expectedInterval), gapIntervalSearcher.search(searchIntervals, intervals))
    }


    /*
    search interval ---------|xxxxxxxx|-------
    intervals       --|xx|--------|xxxxx|-----

    expected        ---------|xxxx|-----------

     */
    @Test
    fun `given not overlapping interval before search interval, when overlapping interval ends after search interval does and search executed, then expected interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 13, endHour = 16)
        )

        val searchIntervals = createInterval(startHour = 10, endHour = 14)
        val expectedInterval = createInterval(startHour = 10, endHour = 13)
        assertEquals(listOf(expectedInterval), gapIntervalSearcher.search(searchIntervals, intervals))
    }

    /*
    search interval ---------|xxxxxxxx|-------
    intervals       --|xx|-----|xxx|----------

    expected        ---------|x|---|xx|-------

     */
    @Test
    fun `given not overlapping interval before search interval, when overlapping interval is between search interval and search executed, then expected interval is returned`() {
        val intervals = listOf(
            createInterval(startHour = 8, endHour = 9), createInterval(startHour = 11, endHour = 12)
        )

        val searchIntervals = createInterval(startHour = 10, endHour = 14)
        val expectedInterval1 = createInterval(startHour = 10, endHour = 11)
        val expectedInterval2 = createInterval(startHour = 12, endHour = 14)
        assertEquals(listOf(expectedInterval1, expectedInterval2), gapIntervalSearcher.search(
            searchIntervals,
            intervals
        ))
    }

    @ParameterizedTest
    @MethodSource("getMultipleOverlappingIntervalTestRecord")
    fun `given multiple overlapping interval, when search executed, then expected intervals are returned`(testRecord: TestRecord) {
        testRecord.expectedInterval.forEach{
            interval -> interval.toString()
        }
        val actual = gapIntervalSearcher.search(testRecord.searchInterval, testRecord.intervals)
        assertEquals(
            testRecord.expectedInterval, actual
        )
    }

    companion object {
        @JvmStatic
        private fun getMultipleOverlappingIntervalTestRecord(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(/*
                    search interval ---------|xxxxxxxxxx|-------
                    intervals       -----------|xx|-|x|---------

                    expected        ---------|x|--|x|-|x|--------
                    */
                    TestRecord(
                        createInterval(startHour = 10, endHour = 18),
                        listOf(
                            createInterval(startHour = 11, endHour = 12),
                            createInterval(startHour = 13, endHour = 14)
                        ),
                        listOf(
                            createInterval(startHour = 10, endHour = 11),
                            createInterval(startHour = 12, endHour = 13),
                            createInterval(startHour = 14, endHour = 18)
                        )
                    )
                ),
                Arguments.of(
                    /*
                    search interval ---------|xxxxxxxx|-------
                    intervals       ---------|xx|--|x|--------

                    expected        ------------|xx|----------
                    */
                    TestRecord(
                        createInterval(startHour = 10, endHour = 18),
                        listOf(
                            createInterval(startHour = 10, endHour = 12),
                            createInterval(startHour = 14, endHour = 15)
                        ),
                        listOf(
                            createInterval(startHour = 12, endHour = 14),
                            createInterval(startHour = 15, endHour = 18),
                        )
                    )
                ),
                Arguments.of(
                    /*
                    search interval ---------|xxxxxxxx|-------
                    intervals       ----------|xx|--|x|-------

                    expected        -------------|xx|---------
                    */
                    TestRecord(
                        createInterval(startHour = 10, endHour = 14),
                        listOf(
                            createInterval(startHour = 11, endHour = 12),
                            createInterval(startHour = 13, endHour = 14)
                        ), listOf(
                            createInterval(startHour = 10, endHour = 11),
                            createInterval(startHour = 12, endHour = 13)
                        )
                    )
                ),
                Arguments.of(
                    /*
                     search interval ---------|xxxxxxxx|-------
                     intervals       ------------|xxx|x|-------

                     expected        ---------|xx|-------------
                      */
                    TestRecord(
                        createInterval(startHour = 10, endHour = 14),
                        listOf(
                            createInterval(startHour = 11, endHour = 13),
                            createInterval(startHour = 13, endHour = 14)
                        ),
                        listOf(createInterval(startHour = 10, endHour = 11))
                    )
                ),
                Arguments.of(
                    /*
                    search interval ---------|xxxxxxxx|-------
                    intervals       ---------|xxx|x|---------

                    expected        ---------------|xx|-------

                     */
                    TestRecord(
                        createInterval(startHour = 10, endHour = 14),
                        listOf(
                            createInterval(startHour = 10, endHour = 11),
                            createInterval(startHour = 11, endHour = 12)
                        ),
                        listOf(
                            createInterval(startHour = 12, endHour = 14)
                        )
                    )
                ),
                Arguments.of(
                    /*
                    search interval ---------|xxxxxxxx|-------
                    intervals       -------|xxxx|x|-----------

                    expected        ---------------|xx|-------

                     */
                    TestRecord(
                        searchInterval = createInterval(startHour = 10, endHour = 16),
                        intervals = listOf(
                            createInterval(startHour = 8, endHour = 11),
                            createInterval(startHour = 11, endHour = 12)
                        ),
                        expectedInterval = listOf(
                            createInterval(startHour = 12, endHour = 16)
                        )
                    )
                ),
                Arguments.of(
                    /*
                    search interval ---------|xxxxxxxx|-------
                    intervals       -------------|x|xxxx|-----

                    expected        ---------|xxx|------------

                     */
                    TestRecord(
                        searchInterval = createInterval(startHour = 8, endHour = 14),
                        intervals = listOf(
                            createInterval(startHour = 10, endHour = 11),
                            createInterval(startHour = 11, endHour = 17),
                        ),
                        expectedInterval = listOf(
                            createInterval(startHour = 8, endHour = 10)
                        )
                    )
                )


            )
        }

        data class TestRecord(
            val searchInterval: Interval,
            val intervals: List<Interval>,
            val expectedInterval: List<Interval>
        )

        private fun createInterval(startHour: Int, startMinute: Int = 0, endHour: Int, endMinute: Int = 0) = Interval(
            DateTime(1970, 1, 1, startHour, startMinute),
            DateTime(1970, 1, 1, endHour, endMinute)
        )

    }
}
