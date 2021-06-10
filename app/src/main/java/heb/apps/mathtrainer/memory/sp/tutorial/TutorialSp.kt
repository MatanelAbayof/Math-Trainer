package heb.apps.mathtrainer.memory.sp.tutorial

import android.content.Context
import heb.apps.mathtrainer.memory.sp.BaseSharedPreferences
import heb.apps.mathtrainer.memory.sp.SharedPreferencesManager

class TutorialSp(ctx: Context)
    : BaseSharedPreferences(ctx, SharedPreferencesManager.TUTORIAL_SP_NAME) {

    var showMainScreenTutorial: Boolean
    get() = sp.getBoolean(Keys.SHOW_MAIN_SCREEN_TUTORIAL_KEY, true)
    set(value) {
        sp.edit().putBoolean(Keys.SHOW_MAIN_SCREEN_TUTORIAL_KEY, value).apply()
    }

    var showGameTutorial: Boolean
    get() = sp.getBoolean(Keys.SHOW_GAME_TUTORIAL_KEY, true)
    set(value) {
        sp.edit().putBoolean(Keys.SHOW_GAME_TUTORIAL_KEY, value).apply()
    }

    companion object {
        private object Keys {
            const val SHOW_MAIN_SCREEN_TUTORIAL_KEY = "showMainScreenTutorial"
            const val SHOW_GAME_TUTORIAL_KEY = "showGameTutorial"
        }
    }
}