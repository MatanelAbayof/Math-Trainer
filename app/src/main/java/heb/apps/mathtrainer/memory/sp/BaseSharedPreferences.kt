package heb.apps.mathtrainer.memory.sp

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import heb.apps.mathtrainer.utils.json.JSONObj

abstract class BaseSharedPreferences(val ctx: Context, private val name: String)  {

    // memory file
    protected val sp: SharedPreferences = ctx.getSharedPreferences(name, Context.MODE_PRIVATE)

    // put JSON
    @SuppressLint("ApplySharedPref")
    protected fun putJSON(key: String, jsonObj: JSONObj) {
        sp.edit().putString(key, jsonObj.toString()).commit()
    }

    // get JSON
    protected fun getJSON(key: String, defValue: JSONObj? = null) : JSONObj? {
        return when(val resJsonStr = sp.getString(key, null)) {
            null -> defValue
            else -> JSONObj(resJsonStr)
        }
    }

    // clear all data
    fun clearAll() {
        sp.edit().clear().commit()
    }

    // convert to string
    override fun toString(): String = "BaseSharedPreferences: { name=$name }"
}