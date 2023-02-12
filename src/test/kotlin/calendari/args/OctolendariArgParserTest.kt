package calendari.args

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate

class OctolendariArgParserTest {

    @Test
    fun test() {
        val configurationsFolder = Paths.get("src", "test", "resources", "calendar", "configuration")
        val args: Array<String> = arrayOf(
            "--start",
            "2023-02-12",
            "--end",
            "2023-02-14",
            "--calendars",
            configurationsFolder.resolve("google-test.calendar").toString(),
            configurationsFolder.resolve("google-test.calendar").toString(),
            "--output",
            configurationsFolder.resolve("query.txt").toString()
        )
        val configuration = OctolendariArgParser().parse(args)
        val expected = OctolendariConfiguration(
            LocalDate.parse("2023-02-12"),
            LocalDate.parse("2023-02-14"),
            listOf(
                File(configurationsFolder.resolve("google-test.calendar").toString()),
                File(configurationsFolder.resolve("google-test.calendar").toString())
            ),
            File(configurationsFolder.resolve("query.txt").toString())
        )
        assertEquals(expected, configuration)
    }
}