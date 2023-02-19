package calendari.query.factory

import calendari.args.OctolendariConfiguration
import calendari.query.Query

abstract class QueryFactory(private val configuration: OctolendariConfiguration) {
    abstract fun create() : Query
}