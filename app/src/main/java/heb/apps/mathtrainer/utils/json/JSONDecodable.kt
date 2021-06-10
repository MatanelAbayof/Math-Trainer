package heb.apps.mathtrainer.utils.json

import android.content.Context
import org.json.JSONArray
import org.json.JSONException

interface JSONDecodable<T> {
    // name of JSON object for hold list of T
    val listJsonObjName: String

    // parse T from JSON object
    fun parse(jsonObj: JSONObj) : T

    // parse list of objects
    fun parseList(jsonObj: JSONObj) : List<T> {
        val objects = mutableListOf<T>()
        try {
            val objectsJSONArr = jsonObj.getJSONArray(listJsonObjName)
            for(i in 0 until objectsJSONArr.length()) {
                val objectJSONObj = objectsJSONArr.getJSONObject(i).asJSONObj()
                val obj = parse(objectJSONObj)
                objects.add(obj)
            }
        } catch (ex: Throwable) {
            throw JSONException("Cannot parse list of $listJsonObjName from JSON. internal error: ${ex.message}")
        }
        return objects
    }

    // parse list of objects
    fun parseList(jsonArr: JSONArray): List<T> {
        val objects = mutableListOf<T>()
        try {
            for (i in 0 until jsonArr.length()) {
                val objectJSONObj = jsonArr.getJSONObject(i)!!.asJSONObj()
                val obj = parse(objectJSONObj)
                objects.add(obj)
            }
        } catch (ex: Throwable) {
            throw JSONException("Cannot parse list of $listJsonObjName from JSON. " +
                    "internal error: ${ex.message}")
        }
        return objects
    }

    // parse list from JSON file
    fun parseList(ctx: Context, jsonResId: Int)  : List<T> {
        try {
            val jsonObj = JSONHelper.readJSONObj(ctx, jsonResId)
            return parseList(jsonObj)
        }catch (ex: Throwable) {
            throw JSONException("Cannot parse list from jsonResId=$jsonResId. internal error: ${ex.message}")
        }
    }
}

