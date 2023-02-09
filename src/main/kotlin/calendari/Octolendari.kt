package calendari

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.required

class Octolendari {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val parser = ArgParser("octolendari")
            val input = parser.option(ArgType.String, shortName = "i", description = "Input file").required()
            val output = parser.option(ArgType.String, shortName = "o", description = "Output file name")
        }
    }
}