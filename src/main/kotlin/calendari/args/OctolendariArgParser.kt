package calendari.args

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required
import kotlinx.cli.vararg
import java.io.File
import java.time.LocalDate

class OctolendariArgParser {
    fun parse(args: Array<String>): OctolendariConfiguration {
        val parser = ArgParser("octolendari")
        val start by parser.option(ArgType.String,  description = "Start date of query").required()
        val end by parser.option(ArgType.String,  description = "end date of query").required()
        val calendars by parser.argument(ArgType.String).vararg()
        val output by parser.option(ArgType.String, description = "Input file").required()
        parser.parse(args)

        val calendarsList = arrayListOf<File>()
        for (calendar in calendars.subList(1,calendars.lastIndex+1)){
            calendarsList.add(File(calendar))
        }
        return OctolendariConfiguration(LocalDate.parse(start), LocalDate.parse(end), calendarsList, File(output))
    }
}