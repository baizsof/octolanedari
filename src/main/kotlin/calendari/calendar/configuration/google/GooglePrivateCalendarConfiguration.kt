package calendari.calendar.configuration.google

import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties

class GooglePrivateCalendarConfiguration(
    val tokenPath: Path,
    val credentialFilePath: Path,
) {
    companion object {
        @JvmStatic
        fun fromProperties(properties: Properties): GooglePrivateCalendarConfiguration {
            val tokenPath = Paths.get(properties.getProperty("token-path").toString())
            val credentialFilePath = Paths.get(properties.getProperty("credential-file-path").toString())
            return GooglePrivateCalendarConfiguration(tokenPath, credentialFilePath)
        }
    }
}