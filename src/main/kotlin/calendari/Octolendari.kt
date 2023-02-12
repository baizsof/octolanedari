package calendari

import calendari.args.OctolendariArgParser
import calendari.args.OctolendariConfiguration

class Octolendari {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val configuration: OctolendariConfiguration = OctolendariArgParser().parse(args)
        }
    }
}