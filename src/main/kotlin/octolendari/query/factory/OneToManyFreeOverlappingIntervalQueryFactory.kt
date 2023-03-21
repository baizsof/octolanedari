package octolendari.query.factory

import octolendari.args.OctolendariConfiguration
import octolendari.calendar.Calendar
import octolendari.calendar.configuration.PublicCalendarConfiguration
import octolendari.calendar.configuration.google.GooglePublicCalendarConfiguration
import octolendari.calendar.configuration.TeamupCalendarConfiguration
import octolendari.calendar.configuration.google.GooglePrivateCalendarConfiguration
import octolendari.calendar.connector.CalendarConnector
import octolendari.calendar.connector.google.GooglePublicCalendarConnector
import octolendari.calendar.connector.TeamupCalendarConnector
import octolendari.calendar.connector.google.GooglePrivateCalendarConnector
import octolendari.query.OneToManyFreeOverlappingIntervalQuery
import octolendari.query.Query
import java.util.Properties

class OneToManyFreeOverlappingIntervalQueryFactory(private val configuration: OctolendariConfiguration) : QueryFactory(configuration) {
    override fun create(): Query {
        //TODO: OCT-1 revise QueryFactory functionality
        //TODO: OCT-2 increase code coverage for QueryFactory
        val oneCalendarProperty : Properties = this.configuration.calendarConfigurationProperties.first { it.containsValue("one") }
        val oneCalendar = getCalendar(oneCalendarProperty)
        val manyCalendars = arrayListOf<Calendar>()
        for (manyCalendarProperty in configuration.calendarConfigurationProperties.filter { it.containsValue("many") }){
            manyCalendars.add(getCalendar(manyCalendarProperty))
        }
        return OneToManyFreeOverlappingIntervalQuery(oneCalendar, manyCalendars, configuration.start, configuration.end)
    }

    private fun getCalendar(oneCalendarProperties: Properties): Calendar {
        val calendarConfiguration: PublicCalendarConfiguration
        val oneCalendarConnector: CalendarConnector
        if (oneCalendarProperties.getProperty("connector").equals("google")) {
            if(oneCalendarProperties.getProperty("auth").equals("public")){
                calendarConfiguration = GooglePublicCalendarConfiguration.fromProperties(oneCalendarProperties)
                oneCalendarConnector = GooglePublicCalendarConnector(calendarConfiguration)
                return Calendar(oneCalendarConnector, "google")
            } else {
                val privateCalendarConfiguration = GooglePrivateCalendarConfiguration.fromProperties(oneCalendarProperties)
                val privateCalendarConnector = GooglePrivateCalendarConnector(privateCalendarConfiguration)
                return Calendar(privateCalendarConnector, "google")
            }
        } else {
            calendarConfiguration = TeamupCalendarConfiguration.fromProperties(oneCalendarProperties)
            oneCalendarConnector = TeamupCalendarConnector(calendarConfiguration)
            return Calendar(oneCalendarConnector, "teamup")
        }
    }

}