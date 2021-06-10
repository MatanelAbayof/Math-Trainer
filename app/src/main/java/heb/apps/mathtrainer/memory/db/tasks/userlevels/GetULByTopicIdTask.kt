package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetULByTopicIdTask(ctx: Context) : MathDBTask<Int, List<UserLevel>>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): List<UserLevel>? { // Note: input = topicId
        require(input != null) {
            "Cannot have topic id at input"
        }
        return db.userLevelDao().getULByTopicId(input)
    }

}