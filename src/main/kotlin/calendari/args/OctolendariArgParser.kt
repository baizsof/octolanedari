package calendari.args

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.io.File
import java.time.LocalDate

class OctolendariArgParser {
    fun parse(args: Array<String>): OctolendariConfiguration {
        val parser = ArgParser("octolendari")
        val start = parser.option(ArgType.String, shortName = "s", description = "Start date of query").required()
        val end = parser.option(ArgType.String, shortName = "e", description = "end date of query").required()
        parser.parse(args)
        return OctolendariConfiguration(LocalDate.parse(start.value), LocalDate.parse(end.value), listOf(), File(""))
    }
}