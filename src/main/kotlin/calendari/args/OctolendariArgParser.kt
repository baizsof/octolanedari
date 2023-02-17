package calendari.args

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate

class OctolendariArgParser {
    fun parse(args: Array<String>): OctolendariConfiguration {
        val parser = ArgParser("octolendari")
        val start by parser.option(ArgType.String,  description = "Start date of query").required()
        val end by parser.option(ArgType.String,  description = "end date of query").required()
        val calendars by parser.option(ArgType.String).required()
        val output by parser.option(ArgType.String, description = "Input file").required()
        parser.parse(args)

        val calendarConfigurationFiles : List<File> = File(calendars).listFiles{ it -> it.extension == "calendar" }?.toList() ?: throw FileNotFoundException("Could not found .configuration file(s) at the given path $calendars")
        return OctolendariConfiguration(LocalDate.parse(start), LocalDate.parse(end), calendarConfigurationFiles, File(output))
    }
}