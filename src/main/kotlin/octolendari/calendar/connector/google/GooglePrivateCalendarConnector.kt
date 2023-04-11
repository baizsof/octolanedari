package octolendari.calendar.connector.google

import octolendari.calendar.event.ReservedEvent
import octolendari.calendar.configuration.google.GooglePrivateCalendarConfiguration
import octolendari.calendar.connector.CalendarConnector
import octolendari.calendar.mapper.google.GooglePrivateApiEventMapper
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Events
import octolendari.calendar.event.Event
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.ZoneId
import java.util.Collections
import java.util.Date
import kotlin.io.path.inputStream


class GooglePrivateCalendarConnector(
    private val configuration: GooglePrivateCalendarConfiguration
) : CalendarConnector {
    private val APPLICATION_NAME = "Octolendari"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()

    private val SCOPES: List<String> = Collections.singletonList(CalendarScopes.CALENDAR_READONLY)

    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential? {
        val input: InputStream = configuration.credentialFilePath.inputStream()
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(input))

        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(configuration.tokenPath.toFile()))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    override fun getEvents(from: LocalDate, to: LocalDate): List<Event> {
        val googleEvents: List<com.google.api.services.calendar.model.Event> = getGoogleEvents(from, to)

        val calendariReservedEvents = arrayListOf<ReservedEvent>()
        val mapper = GooglePrivateApiEventMapper()
        for (item in googleEvents) {
            calendariReservedEvents.add(mapper.getEvent(item))
        }

        return calendariReservedEvents
    }

    private fun getGoogleEvents(
        from: LocalDate,
        to: LocalDate
    ): List<com.google.api.services.calendar.model.Event> {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val service: Calendar = Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()

        val googleFrom = DateTime(Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant()))
        val googleTo = DateTime(Date.from(to.atStartOfDay(ZoneId.systemDefault()).toInstant()))
        val googleEvents: Events = service.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(googleFrom)
            .setTimeMax(googleTo)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items: List<com.google.api.services.calendar.model.Event> = googleEvents.getItems()
        return items
    }
}