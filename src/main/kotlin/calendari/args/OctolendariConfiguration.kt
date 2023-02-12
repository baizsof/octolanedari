package calendari.args

import java.io.File
import java.time.LocalDate

data class OctolendariConfiguration(
    private val start: LocalDate,
    private val end: LocalDate,
    private val calendarConfigurationFiles: List<File>,
    private val output : File
)
