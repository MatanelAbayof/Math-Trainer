package heb.apps.mathtrainer.memory.db.tasks.topics

import android.content.Context
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetTopicByIdTask(ctx: Context) : MathDBTask<Int, Topic>(ctx) {

    // input = topic id
    override fun doInBack(db: MathDB, input: Int?): Topic? {
        return when(val topic = db.topicDao().getTopicById(input!!)) {
            null -> throw IllegalArgumentException("Cannot find topic with id=$input at DB")
            else -> topic
        }
    }
}