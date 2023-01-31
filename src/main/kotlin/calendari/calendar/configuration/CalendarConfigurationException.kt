package calendari.calendar.configuration

class CalendarConfigurationException(msg : String) : Exception(msg){
    companion object {
        val TIME_ZONE_REQUIRED = "Timezone is required for calendar configuration"
        val CALENDAR_ID_REQUIRED = "Calendar id is required for calendar configuration"
    }
}