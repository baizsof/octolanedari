package octolendari.args

import java.io.File
import java.time.LocalDate
import java.util.Properties

data class OctolendariConfiguration(
    val start: LocalDate,
    val end: LocalDate,
    val calendarConfigurationProperties: List<Properties>,
    val output : File
)
