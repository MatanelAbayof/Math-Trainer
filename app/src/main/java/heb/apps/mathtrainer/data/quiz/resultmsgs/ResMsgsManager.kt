package heb.apps.mathtrainer.data.quiz.resultmsgs

import android.content.Context
import heb.apps.mathtrainer.R
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONObj
import heb.apps.mathtrainer.utils.json.map
import org.json.JSONException

class ResMsgsManager(ctx: Context) {
    private val msgsInfo: List<MsgsInfo> = MsgsInfo.parseList(ctx, R.raw.msgs_stars)

    // random message
    fun randMsg(numOfStars: Int) : String {
        val msgsInfo = msgsInfo.find { it.numOfStars == numOfStars }
        require(msgsInfo != null) {
            "Cannot find 'MsgsInfo' with numOfStars=$numOfStars"
        }
        return msgsInfo.msgs.random()
    }

    // messages info
    private data class MsgsInfo(val numOfStars: Int, val msgs: List<String>) {

        companion object : JSONDecodable<MsgsInfo> {
            override val listJsonObjName: String = "msgs"

            // parse from JSON object
            override fun parse(jsonObj: JSONObj) : MsgsInfo {
                try {
                    val numOfStars = jsonObj.getInt("stars")
                    val messages = jsonObj.getJSONArray("messages").map { it.toString() }
                    return MsgsInfo(numOfStars, messages)
                } catch (ex: JSONException) {
                    throw JSONException("Cannot parse 'MsgsInfo' from JSON. internal error: ${ex.message}")
                }
            }
        }
    }
}