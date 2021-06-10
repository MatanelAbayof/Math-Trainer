package heb.apps.mathtrainer.memory.db.tasks.levels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetLevelsTask(ctx: Context) : MathDBTask<Void, List<Level>>(ctx) {

    override fun doInBack(db: MathDB, input: Void?): List<Level>? = db.levelDao().getLevels()

}