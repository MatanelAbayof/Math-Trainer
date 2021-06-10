package heb.apps.mathtrainer.data.quiz.builder

import android.content.Context
import heb.apps.mathtrainer.utils.json.JSONObj

object QuizJSONBuilder { // Note: this class used only for pre-production

    private const val LOG_TAG = "QuizJSONBuilder"

    val jsonsMap = mutableMapOf<String, () -> JSONObj>()

    // build all JSONs
    fun prepareAllJSONs(ctx: Context) {
        CategoriesBuilder(ctx)
        TopicsBuilder(ctx)
        LevelsBuilder(ctx)
    }

}