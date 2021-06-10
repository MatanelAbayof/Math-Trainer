package heb.apps.mathtrainer.memory.db.tasks.userlevels

import android.content.Context
import heb.apps.mathtrainer.data.quiz.level.UserLevel
import heb.apps.mathtrainer.memory.db.MathDB
import heb.apps.mathtrainer.memory.db.tasks.MathDBTask

class InsUpUserLevelTask(ctx: Context) : MathDBTask<UserLevel, Void>(ctx) {

    override fun doInBack(db: MathDB, input: UserLevel?): Void? {
        if(input != null) {
            val userLevelsDoa = db.userLevelDao()
            userLevelsDoa.insertOrUpdate(input)
        }
        return null
    }

}