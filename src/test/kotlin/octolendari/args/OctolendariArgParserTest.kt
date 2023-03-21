package octolendari.args

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.util.Properties
import kotlin.test.Ignore

class OctolendariArgParserTest {

    @Test
    @Ignore
    fun `given valid arguments, when argparser called, then expected config is returned`() {
        val configurationsFolder = Paths.get("src", "test", "resources", "calendar", "configuration")
        val googleTestProperties = Properties()
        googleTestProperties.load(File(configurationsFolder.resolve("google-test.calendar").toString()).reader())
        val teamupTestProperties = Properties()
        teamupTestProperties.load(File(configurationsFolder.resolve("teamup-test.calendar").toString()).reader())
        val args: Array<String> = arrayOf(
            "--start",
            "2023-02-12",
            "--end",
            "2023-02-14",
            "--calendars",
            configurationsFolder.toString(),
            "--output",
            configurationsFolder.resolve("query.txt").toString()
        )
        val configuration = OctolendariArgParser().parse(args)
        val expected = OctolendariConfiguration(
            LocalDate.parse("2023-02-12"),
            LocalDate.parse("2023-02-14"),
            listOf(
                googleTestProperties,
                teamupTestProperties
            ),
            File(configurationsFolder.resolve("query.txt").toString())
        )
        assertEquals(expected, configuration)
    }

}