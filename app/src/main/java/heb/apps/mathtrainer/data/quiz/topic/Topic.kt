package heb.apps.mathtrainer.data.quiz.topic

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import heb.apps.mathtrainer.data.quiz.category.Category
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import org.json.JSONException

@Entity(tableName = Topic.TABLE_NAME,
        foreignKeys = [ForeignKey(entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.NO_ACTION)]
)
data class Topic(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                 @ColumnInfo(index = true) var categoryId: Int = 0,
                 var uniqueName: String = "",
                 var displayName: String = "",
                 var description: String = "",
                 var tutorialLink: String? = null,
                 var imagePath: String? = null,
                 var orderIndex: Int = 0)
    : JSONEncodable {

    // convert to JSON object
    override fun toJSON() : JSONObj {
        val jsonObj = JSONObj()
        jsonObj.put("id", id)
        jsonObj.put("categoryId", categoryId)
        jsonObj.put("uniqueName", uniqueName)
        jsonObj.put("displayName", displayName)
        jsonObj.put("description", description)
        jsonObj.put("tutorialLink", tutorialLink)
        jsonObj.put("imagePath", imagePath)
        jsonObj.put("orderIndex", orderIndex)
        return jsonObj
    }

    companion object : JSONDecodable<Topic> {
        override val listJsonObjName: String = "topics"
        // name of table in DB
        const val TABLE_NAME = "Topics"

        // parse from JSON object
        override fun parse(jsonObj: JSONObj) : Topic {
            val topic = Topic()
            try {
                topic.id = jsonObj.getInt("id")
                topic.categoryId = jsonObj.getInt("categoryId")
                topic.uniqueName = jsonObj.getString("uniqueName")
                topic.displayName = jsonObj.getString("displayName")
                topic.description = jsonObj.getString("description")
                topic.tutorialLink = jsonObj.optString("tutorialLink")
                topic.imagePath = jsonObj.optString("imagePath")
                topic.orderIndex = jsonObj.getInt("orderIndex")
            } catch (ex: JSONException) {
                throw JSONException("Cannot parse topic from JSON. internal error: ${ex.message}")
            }
            return topic
        }
    }
} 

