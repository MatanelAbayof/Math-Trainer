package heb.apps.mathtrainer.data.quiz.level

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import kotlinx.android.parcel.Parcelize
import org.json.JSONException

@Entity(tableName = Level.TABLE_NAME,
    foreignKeys = [ForeignKey(entity = Topic::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("topicId"),
        onDelete = ForeignKey.NO_ACTION)]
)
@Parcelize
data class Level(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                 var uniqueName: String = "",
                 var displayName: String = "",
                 @ColumnInfo(index = true) var topicId: Int = 0,
                 var difficulty: Int = 0,
                 var tutorialTextInfo: String? = null,
                 var tutorialLink: String? = null,
                 var imagePath: String? = null,
                 var xpPerStar: Int = 0,
                 var minWinTime: Long = 0L)
    : JSONEncodable, Parcelable {

    /** convert to JSON object */
    override fun toJSON() : JSONObj {
        val jsonObj = JSONObj()
        jsonObj.put("id", id)
        jsonObj.put("uniqueName", uniqueName)
        jsonObj.put("displayName", displayName)
        jsonObj.put("topicId", topicId)
        jsonObj.put("difficulty", difficulty)
        jsonObj.put("tutorialTextInfo", tutorialTextInfo)
        jsonObj.put("tutorialLink", tutorialLink)
        jsonObj.put("imagePath", imagePath)
        jsonObj.put("xpPerStar", xpPerStar)
        jsonObj.put("minWinTime", minWinTime)
        return jsonObj
    }

    companion object : JSONDecodable<Level> {
        override val listJsonObjName: String = "levels"
        /** name of table in DB */
        const val TABLE_NAME = "Levels"

        /** parse from JSON object */
        override fun parse(jsonObj: JSONObj) : Level {
            val level = Level()
            try {
                level.id = jsonObj.getInt("id")
                level.uniqueName = jsonObj.getString("uniqueName")
                level.displayName = jsonObj.getString("displayName")
                level.topicId = jsonObj.getInt("topicId")
                level.difficulty = jsonObj.getInt("difficulty")
                level.tutorialTextInfo = jsonObj.optString("tutorialTextInfo")
                level.tutorialLink = jsonObj.optString("tutorialLink")
                level.imagePath = jsonObj.optString("imagePath")
                level.xpPerStar = jsonObj.getInt("xpPerStar")
                level.minWinTime = jsonObj.getLong("minWinTime")
            } catch (ex: JSONException) {
                throw JSONException("Cannot parse level from JSON. internal error: ${ex.message}")
            }
            return level
        }
    }
}