package heb.apps.mathtrainer.utils.json

import android.content.Context
import androidx.annotation.RawRes
import heb.apps.mathtrainer.utils.string.StrHelper
import org.json.JSONArray
import org.json.JSONException

object JSONHelper {

    /** read JSON from raw resources */
    fun readJSONObj(ctx: Context, @RawRes resId: Int) : JSONObj {
        try {
            return JSONObj(StrHelper.readStr(ctx, resId))
        } catch (e: Exception) {
            throw JSONException("Cannot read JSON object from resourceId=$resId." +
                    " details: ${e.message}")
        }
    }

    /** read JSON array */
    fun readJSONArr(ctx: Context, @RawRes resId: Int) : JSONArray {
        try {
            return JSONArray(StrHelper.readStr(ctx, resId))
        } catch (e: Exception) {
            throw JSONException("Cannot read JSON array from resourceId=$resId." +
                    " details: ${e.message}")
        }
    }
}

/** map JSON array */
fun <T> JSONArray.map(mapFunc: (obj: Any) -> T) : List<T> {
    val resList = mutableListOf<T>()
    forEach {
        resList.add(mapFunc(it))
    }
    return resList
}

/** for each at JSON array */
fun JSONArray.forEach(onGetElement: (obj: Any) -> Unit) {
    for(i in 0 until length()) {
        val obj = get(i)
        onGetElement(obj)
    }
}