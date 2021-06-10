package heb.apps.mathtrainer.utils

import android.os.Bundle
import heb.apps.mathtrainer.utils.json.JSONObj

/** get JSON from bundle */
fun Bundle.getJSON(key: String) : JSONObj {
    val jsonStr = getString(key)
    require(jsonStr != null) {
        "Cannot find JSON with key='$key'"
    }
    return JSONObj(jsonStr)
}

/** put JSON at bundle */
fun Bundle.putJSON(key: String, json: JSONObj) {
    putString(key, json.toString())
}

/** put JSON of object at bundle */
//fun Bundle.putJSON(key: String, obj: JSONEncodable) {
//    putJSON(key, obj.toJSON())
//}