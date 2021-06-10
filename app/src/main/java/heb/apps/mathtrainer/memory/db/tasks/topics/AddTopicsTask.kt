package heb.apps.mathtrainer.memory.db.tasks.topics

import android.content.Context
import heb.apps.mathtrainer.data.quiz.topic.Topic
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class AddTopicsTask(ctx: Context) : MathDBTask<List<Topic>, Void>(ctx) {

    override fun doInBack(db: MathDB, input: List<Topic>?): Void? {
        if(input != null) {
            val topicDao = db.topicDao()
            for(topic in input)
                topicDao.insert(topic)
        }
        return null
    }

}