package calendari.query.factory

import calendari.args.OctolendariConfiguration
import calendari.calendar.Calendar
import calendari.calendar.configuration.CalendarConfiguration
import calendari.calendar.configuration.GoogleCalendarConfiguration
import calendari.calendar.configuration.TeamupCalendarConfiguration
import calendari.calendar.connector.CalendarConnector
import calendari.calendar.connector.GoogleCalendarConnector
import calendari.calendar.connector.TeamupCalendarConnector
import calendari.query.OneToManyFreeOverlappingIntervalQuery
import calendari.query.Query
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
        val calendarConfiguration: CalendarConfiguration
        val oneCalendarConnector: CalendarConnector
        if (oneCalendarProperties.getProperty("connector").equals("google")) {
            calendarConfiguration = GoogleCalendarConfiguration.fromProperties(oneCalendarProperties)
            oneCalendarConnector = GoogleCalendarConnector(calendarConfiguration)
            return Calendar(oneCalendarConnector, "google")
        } else {
            calendarConfiguration = TeamupCalendarConfiguration.fromProperties(oneCalendarProperties)
            oneCalendarConnector = TeamupCalendarConnector(calendarConfiguration)
            return Calendar(oneCalendarConnector, "teamup")
        }
    }

}