package heb.apps.mathtrainer.data.quiz.level

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import kotlinx.android.parcel.Parcelize
import org.json.JSONException

@Entity(tableName = UserLevel.TABLE_NAME,
	foreignKeys = [ForeignKey(entity = Level::class,
		parentColumns = arrayOf("id"),
		childColumns = arrayOf("levelId"),
		onDelete = ForeignKey.NO_ACTION)]
)
@Parcelize
data class UserLevel(@PrimaryKey(autoGenerate = true) var id: Int = 0,
					 @ColumnInfo(index = true) var levelId: Int = 0,
					 var minFinishTime: Long? = null,
					 var numOfFilledStars: Int = 0) : JSONEncodable, Parcelable {

	// convert to JSON object
	override fun toJSON() : JSONObj {
		val jsonObj = JSONObj()
		jsonObj.put("id", id)
		jsonObj.put("levelId", levelId)
		jsonObj.put("minFinishTime", minFinishTime)
		jsonObj.put("numOfFilledStars", numOfFilledStars)
		return jsonObj
	}

	companion object : JSONDecodable<UserLevel> {
		override val listJsonObjName: String = "userLevels"
		// name of table in DB
		const val TABLE_NAME = "UserLevels"

		// parse from JSON object
		override fun parse(jsonObj: JSONObj) : UserLevel {
			val userLevel = UserLevel()
			try {
				userLevel.id = jsonObj.getInt("id")
				userLevel.levelId = jsonObj.getInt("levelId")
				userLevel.minFinishTime = jsonObj.optLong("minFinishTime")
				userLevel.numOfFilledStars = jsonObj.getInt("numOfFilledStars")
			} catch (ex: JSONException) {
				throw JSONException("Cannot parse user level from JSON. internal error: " +
						"${ex.message}")
			}
			return userLevel
		}
	}
}