package calendari.calendar.parser

import org.json.JSONObject

class FakeJsonWebCalendarParser : JsonWebCalendarParser() {

    private lateinit var json: JSONObject

    fun setReturnJSONObject(json: JSONObject) {
        this.json = json
    }

    override fun parse() : JSONObject {
        return json
    }



}