package heb.apps.mathtrainer.utils.string

import android.content.Context
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import org.json.JSONException

/** format string */
fun String.Companion.format(ctx: Context, @StringRes resId: Int, vararg args: Any?) : String
 = format(ctx.getString(resId), *args)


object StrHelper {
    /** read string from resource */
    fun readStr(ctx: Context, @RawRes resId: Int) : String {
        try {
            return ctx.resources.openRawResource(resId)
                .bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            throw JSONException("Cannot read string from resourceId=$resId." +
                    " details: ${e.message}")
        }
    }
}
