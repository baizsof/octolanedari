package octolendari.calendar.parser
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.lang.IllegalStateException
import java.net.URL
import java.nio.charset.StandardCharsets

open class JsonWebCalendarParser : WebCalendarParser<JSONObject> {
    private var url : URL? = null

    override fun parse() : JSONObject {
        if(url == null){
            throw IllegalStateException("Should set url before calling parse.")
        }
        val byteInput = ByteArrayInputStream(url!!.openStream().readBytes())
        return JSONObject(String(byteInput.readBytes(), StandardCharsets.UTF_8))
    }
    override fun setUrl(url: URL) {
        this.url = url
    }
}