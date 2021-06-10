package heb.apps.mathtrainer.memory.db.tasks.categories

import android.content.Context
import heb.apps.mathtrainer.data.quiz.topic.TopicWithExtras
import heb.apps.mathtrainer.memory.db.DBException
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetTopicsWithExtrasTask(ctx: Context) : MathDBTask<Int, List<TopicWithExtras>>(ctx) {

    // do in background
    override fun doInBack(db: MathDB, input: Int?): List<TopicWithExtras>? { // input = category id
        require(input != null) {
            "Are you missed to pass category id as input?"
        }

        val topicDao = db.topicDao()
        val topics = topicDao.getTopicsByCategoryId(input)
        val topicsFilledStars = topicDao.getTopicsFilledStars(input)

        return topics.map { topic ->
            when(val extras = topicsFilledStars.find {
                 topic.id == it.topicId }) {
                    null -> throw DBException("Cannot find extra info of $topic")
                    else -> TopicWithExtras(topic, extras.completePercents)
            }
        }
    }

}