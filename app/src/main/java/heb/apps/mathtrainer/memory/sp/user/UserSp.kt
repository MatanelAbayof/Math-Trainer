package heb.apps.mathtrainer.memory.sp.user

import android.content.Context
import heb.apps.mathtrainer.data.user.UserInfo
import heb.apps.mathtrainer.memory.sp.BaseSharedPreferences
import heb.apps.mathtrainer.memory.sp.SharedPreferencesManager

class UserSp(ctx: Context)
    : BaseSharedPreferences(ctx, SharedPreferencesManager.USER_SP_NAME) {

    var userInfo: UserInfo
    get() = UserInfo.parse(getJSON(Keys.USER_INFO_KEY, UserInfo().toJSON())!!)
    set(value) = putJSON(Keys.USER_INFO_KEY, value.toJSON())

    companion object {
        private object Keys {
            const val USER_INFO_KEY = "userInfo"
        }
    }
}