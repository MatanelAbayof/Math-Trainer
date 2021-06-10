package heb.apps.mathtrainer.memory.db.tasks.levels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.Level
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetLevelsByTopicIdTask(ctx: Context) : MathDBTask<Int, List<Level>>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): List<Level>? { // Note: input = topicId
        require(input != null) {
            "Cannot have topic id at input"
        }
        return db.levelDao().getLevelsByTopicId(input)
    }

}