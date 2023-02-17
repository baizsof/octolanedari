package calendari

import calendari.args.OctolendariArgParser
import calendari.args.OctolendariConfiguration
import calendari.presenter.QueryPresenter
import calendari.presenter.TxtFileQueryPresenter
import calendari.query.Query
import calendari.query.factory.OneToManyFreeOverlappingIntervalQueryFactory
import calendari.query.factory.QueryFactory

class Octolendari {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val configuration: OctolendariConfiguration = OctolendariArgParser().parse(args)
            val queryFactory : QueryFactory = OneToManyFreeOverlappingIntervalQueryFactory(configuration)
            val query : Query = queryFactory.create()
            val eventCandidates = query.doQuery()
            val presenter : QueryPresenter = TxtFileQueryPresenter(configuration.output)
            presenter.present(eventCandidates)
        }
    }
}