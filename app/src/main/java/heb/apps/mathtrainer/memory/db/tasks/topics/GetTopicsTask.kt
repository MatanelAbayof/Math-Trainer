package heb.apps.mathtrainer.memory.db.tasks.topics

import android.content.Context
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class GetTopicsTask(ctx: Context) : MathDBTask<Int, List<Topic>>(ctx) {

    override fun doInBack(db: MathDB, input: Int?): List<Topic>? { // Note: input = categoryId
        val topicDao = db.topicDao()
        var topics = mutableListOf<Topic>()
        if(input != null)
            topics = topicDao.getTopicsByCategoryId(input).toMutableList()
        return topics
    }

}