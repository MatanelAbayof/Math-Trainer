package heb.apps.mathtrainer.memory.sp.app

import android.content.Context
import heb.apps.mathtrainer.memory.sp.BaseSharedPreferences
import heb.apps.mathtrainer.memory.sp.SharedPreferencesManager

class AppSp(ctx: Context)
    : BaseSharedPreferences(ctx, SharedPreferencesManager.APP_SP_NAME) {

    /** lat version code of the application */
    var lastVersionCode: Int
    get() = sp.getInt(Keys.VERSION_NUM_KEY, 1)
    set(value) {
        sp.edit().putInt(Keys.VERSION_NUM_KEY, value).commit()
    }

    companion object {
        private object Keys {
            const val VERSION_NUM_KEY = "versionNum"
        }
    }
}