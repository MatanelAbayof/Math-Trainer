package heb.apps.mathtrainer.data.quiz.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import org.json.JSONException

@Entity(tableName = Category.TABLE_NAME)
data class Category(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                    var uniqueName: String = "",
                    var displayName: String = "",
                    var description: String = "",
                    var descriptionLink: String? = null,
                    var imagePath: String? = null,
                    var orderIndex: Int = 0)
    : JSONEncodable {

    // convert to JSON object
    override fun toJSON() : JSONObj {
        val jsonObj = JSONObj()
        jsonObj.put("id", id)
        jsonObj.put("uniqueName", uniqueName)
        jsonObj.put("displayName", displayName)
        jsonObj.put("description", description)
        jsonObj.put("descriptionLink", descriptionLink)
        jsonObj.put("imagePath", imagePath)
        jsonObj.put("orderIndex", orderIndex)
        return jsonObj
    }

    companion object : JSONDecodable<Category> {
        override val listJsonObjName: String = "categories"
        // name of table in DB
        const val TABLE_NAME = "Categories"

        // parse from JSON object
        override fun parse(jsonObj: JSONObj) : Category {
            val category = Category()
            try {
                category.id = jsonObj.getInt("id")
                category.uniqueName = jsonObj.getString("uniqueName")
                category.displayName = jsonObj.getString("displayName")
                category.description = jsonObj.getString("description")
                category.descriptionLink = jsonObj.optString("descriptionLink")
                category.imagePath = jsonObj.optString("imagePath")
                category.orderIndex = jsonObj.getInt("orderIndex")
            } catch (ex: JSONException) {
                throw JSONException("Cannot parse category from JSON. internal error: " +
                        "${ex.message}")
            }
            return category
        }
    }
}