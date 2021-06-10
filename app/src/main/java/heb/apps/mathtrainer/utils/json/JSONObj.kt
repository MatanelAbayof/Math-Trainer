package heb.apps.mathtrainer.utils.json

import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

// convert JSONObject to JSONObj
fun JSONObject.asJSONObj(): JSONObj = JSONObj(this)


class JSONObj(jsonStr: String = "{}") : JSONObject(jsonStr) {

    constructor(jsonObj: JSONObject) : this(jsonObj.toString())

    // get date from JSON object
    fun optDate(key: String, dateFormat: String = DEF_DATE_FORMAT): Date? {
        try {
            val jsonProperty = optString(key)
            if (jsonProperty.isNullOrBlank())
                return null
            return SimpleDateFormat(dateFormat, Locale.getDefault()).parse(jsonProperty)
        } catch (e: Throwable) {
            throw JSONException("Cannot parse date with format='$dateFormat' from JSON property")
        }
    }

    // get date from JSON object
    fun getDate(key: String, dateFormat: String = DEF_DATE_FORMAT): Date {
        try {
            val jsonProperty = getString(key)
            return SimpleDateFormat(dateFormat, Locale.getDefault()).parse(jsonProperty)!!
        } catch (e: Throwable) {
            throw JSONException("Cannot parse date with format='$dateFormat' from JSON property")
        }
    }

    // put date in JSON.
    fun put(key: String, date: Date?, dateFormat: String = DEF_DATE_FORMAT) {
        if (date != null) {
            val dateStr = SimpleDateFormat(dateFormat, Locale.getDefault()).format(date)
            put(key, dateStr)
        }
    }

    /** Return the value mapped by the given key, or `null` if not present or null.  */
    override fun optString(key: String): String?
            = if (isNull(key)) null else super.optString(key)

    companion object {
        // default JSON date format
        private const val DEF_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"
    }
}