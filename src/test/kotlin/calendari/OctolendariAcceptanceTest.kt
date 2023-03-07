package calendari

import org.junit.jupiter.api.Test
import java.nio.file.Paths

class OctolendariAcceptanceTest{
    @Test
    fun test1() {
        val configurationsFolder = Paths.get("src", "test", "resources", "calendar", "configuration")
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
    }
}