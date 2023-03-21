package octolendari

import octolendari.args.OctolendariArgParser
import octolendari.args.OctolendariConfiguration
import octolendari.presenter.QueryPresenter
import octolendari.presenter.TxtFileQueryPresenter
import octolendari.query.Query
import octolendari.query.factory.OneToManyFreeOverlappingIntervalQueryFactory
import octolendari.query.factory.QueryFactory

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