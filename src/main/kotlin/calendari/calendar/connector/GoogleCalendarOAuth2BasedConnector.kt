package calendari.calendar.connector

import calendari.calendar.Event
import calendari.calendar.configuration.GoogleCalendarOAuth2BasedConfiguration
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
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.util.Collections
import java.util.Date


class GoogleCalendarOAuth2BasedConnector(
    private val configuration : GoogleCalendarOAuth2BasedConfiguration) : CalendarConnector{
    private val APPLICATION_NAME = "Octolendari"
    private val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()

    /**
     * Directory to store authorization tokens for this application.
     */
    private val TOKENS_DIRECTORY_PATH = configuration.tokenPath.toAbsolutePath().toString()

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private val SCOPES: List<String> = Collections.singletonList(CalendarScopes.CALENDAR_READONLY)
    private val CREDENTIALS_FILE_PATH = configuration.credentialFilePath.toAbsolutePath().toString()

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    @Throws(IOException::class)
    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential? {
        // Load client secrets.
        val `in`: InputStream =GoogleCalendarOAuth2BasedConnector::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES
        )
            .setDataStoreFactory(FileDataStoreFactory(File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        //returns an authorized Credential object.
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }
    override fun getEvents(from: LocalDate, to: LocalDate): List<Event> {
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()
        val service: Calendar = Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build()

        val googleFrom = DateTime(Date(from.year, from.monthValue, from.dayOfMonth))
        val googleTo = DateTime(Date(to.year,to.monthValue,to.dayOfMonth))
        val events: Events = service.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(googleFrom)
            .setTimeMax(googleTo)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items: List<com.google.api.services.calendar.model.Event> = events.getItems()

        return emptyList()

    }
}