package heb.apps.mathtrainer.memory.db.tasks.levels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class AddLevelsTask(ctx: Context) : MathDBTask<List<Level>, Void>(ctx) {

    override fun doInBack(db: MathDB, input: List<Level>?): Void? {
        if(input != null) {
            val levelsDoa = db.levelDao()
            input.forEach {
                levelsDoa.insert(it)
            }
        }
        return null
    }

}