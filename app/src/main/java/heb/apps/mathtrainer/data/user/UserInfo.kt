package heb.apps.mathtrainer.data.user

import heb.apps.mathtrainer.utils.json.JSONDecodable
import heb.apps.mathtrainer.utils.json.JSONEncodable
import heb.apps.mathtrainer.utils.json.JSONObj
import org.json.JSONException

class UserInfo(var name: String = "", var gender: Gender = Gender.Male) : JSONEncodable {

    enum class Gender {
        Male, Female
    }

    fun isLogged() : Boolean = name.isNotEmpty()

    override fun toString(): String = "UserInfo: { name=$name, gender=$gender }"

    override fun toJSON() : JSONObj {
        val jsonObj = JSONObj()
        jsonObj.put("name", name)
        jsonObj.put("gender", gender.ordinal)
        return jsonObj
    }

    companion object : JSONDecodable<UserInfo> {
        override val listJsonObjName: String = "usersInfo"

        override fun parse(jsonObj: JSONObj): UserInfo {
            try {
                val userInfo = UserInfo()
                userInfo.name = jsonObj.getString("name")
                userInfo.gender = Gender.values()[jsonObj.getInt("gender")]
                return userInfo
            } catch (ex: Throwable) {
                throw JSONException("Cannot parse 'UserInfo' from JSON. the internal error is:" +
                        " ${ex.message}")
            }
        }

    }
}