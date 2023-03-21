package octolendari.query.factory

import octolendari.args.OctolendariConfiguration
import octolendari.query.Query

abstract class QueryFactory(private val configuration: OctolendariConfiguration) {
    abstract fun create() : Query
}