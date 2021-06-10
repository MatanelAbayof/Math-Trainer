package heb.apps.mathtrainer.memory.db.tasks.levels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetLevelByIdTask(ctx: Context) : MathDBTask<Int, Level>(ctx) {

    override fun doInBack(db: MathDB, input: Int?):Level? =
        when(val level = db.levelDao().getLevelById(input!!)) {
        null -> throw IllegalArgumentException("Cannot find level with id=$input at DB")
        else -> level
    }

}