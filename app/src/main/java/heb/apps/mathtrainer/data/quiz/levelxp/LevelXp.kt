package heb.apps.mathtrainer.data.quiz.levelxp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import org.json.JSONException

@Entity(tableName = LevelXp.TABLE_NAME)
data class LevelXp(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                   var xp: Int = 0)
    : JSONEncodable {

    /** convert to JSON object */
    override fun toJSON() : JSONObj {
        val jsonObj = JSONObj()
        jsonObj.put("id", id)
        jsonObj.put("xp", xp)
        return jsonObj
    }

    companion object : JSONDecodable<LevelXp> {
        override val listJsonObjName: String = "levelsXps"
        /** name of table in DB */
        const val TABLE_NAME = "LevelsXps"

        /** parse from JSON object */
        override fun parse(jsonObj: JSONObj) : LevelXp {
            val levelXp = LevelXp()
            try {
                levelXp.id = jsonObj.getInt("id")
                levelXp.xp = jsonObj.getInt("xp")
            } catch (ex: JSONException) {
                throw JSONException("Cannot parse LevelXp from JSON. internal error: ${ex.message}")
            }
            return levelXp
        }
    }
}