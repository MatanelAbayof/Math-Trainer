package heb.apps.mathtrainer.memory.sp.settings

import android.content.Context
import heb.apps.mathtrainer.data.math.PrintInfo
import heb.apps.mathtrainer.memory.sp.BaseSharedPreferences
import heb.apps.mathtrainer.memory.sp.SharedPreferencesManager

class SettingsSp(ctx: Context)
    : BaseSharedPreferences(ctx, SharedPreferencesManager.SETTINGS_SP_NAME)  {

    /** math display type - this is LaTex print mode */
    var mathDisplayType: PrintInfo.LatexPrintMode
    get() {
        val displayTypeIndex = sp.getInt(Keys.MATH_DISPLAY_TYPE_KEY,
                                    PrintInfo.LatexPrintMode.BEGINNER.ordinal)
        return  PrintInfo.LatexPrintMode.values()[displayTypeIndex]
    }
    set(value) {
        val displayTypeIndex = value.ordinal
        sp.edit().putInt(Keys.MATH_DISPLAY_TYPE_KEY, displayTypeIndex).commit()
    }

    companion object {
        private object Keys {
            const val MATH_DISPLAY_TYPE_KEY = "mathDisplayType"
        }
    }
}