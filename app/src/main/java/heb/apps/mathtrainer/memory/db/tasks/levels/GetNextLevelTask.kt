package heb.apps.mathtrainer.memory.db.tasks.levels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetNextLevelTask(ctx: Context) : MathDBTask<Level, Level?>(ctx) {

    override fun doInBack(db: MathDB, input: Level?): Level? { // Note: input = current level
        require(input != null) {
            "Cannot have current level at input"
        }
        return db.levelDao().getNextLevel(input)
    }

}